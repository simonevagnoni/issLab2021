/*
==========================================================================
WebpageServer.js

==========================================================================
*/

const app       = require('express')()
const express   = require('express')
const hhtpSrv   = require('http').Server(app)
const socketIO  = require('socket.io')(hhtpSrv)     //interaction with WebGLScene
const WebSocket = require('ws');                    //interaction with external clients

const sockets       = {}    //interaction with WebGLScene
const wssockets     = {}    //interaction with clients
let socketIndex     = -1
let wssocketIndex   = -1
const serverPort    = 8090

//STATE variables used by cmd handling on wssockets (see initWs)
var alreadyConnected = false
var moveStillRunning = false
var moveHalted       = false
var target           = "notarget"   //the current virtual object that collides

app.use(express.static('./../../WebGLScene'))

/*
-----------------------------------------------------------------------------
Defines how to handle GET from browser and from external controls
-----------------------------------------------------------------------------
*/
    app.get('/', (req, res) => {
	    console.log("WebpageServer | GET socketIndex="+socketIndex + " alreadyConnected =" + alreadyConnected )
        if( ! alreadyConnected ){
            alreadyConnected = true;
            res.sendFile('indexOk.html', { root: './../../WebGLScene' })
	     }else{
		    res.sendFile('indexNoControl.html', { root: './../../WebGLScene' })
	     }
    }); //app.get

/*
-----------------------------------------------------------------------------
Defines how to handle POST from browser and from external controls
-----------------------------------------------------------------------------
*/	//USING POST : by AN Jan 2021
    app.post("/api/move", function(req, res,next)  {
	    var data = ""
	    req.on('data', function (chunk) { data += chunk; }); //accumulate data sent by POST
            req.on('end', function () {	//elaborate data received
			//{ robotmove: move, time:duration } - robotmove: turnLeft | turnRight | ...
			console.log('POST /api/move data ' + data  );
			var jsonData = JSON.parse(data)
     		var moveTodo = jsonData.robotmove
     		var duration = jsonData.time
			doMove(moveTodo, duration, res) //send the answer after duration
  	   });
	}); //app.post

//Execute a robotmove command and sends info about collision
//Possible moveResult : true | false | halted | notallowed
function doMove(moveTodo, duration, res){
	console.log('$$$ WebpageServer doMove |  moveTodo=' + moveTodo + " duration=" + duration);
	execMoveOnAllConnectedScenes(moveTodo, duration)
	setTimeout(function() { //wait for the duration before sending the answer (collision or not)
        if( moveHalted ) moveResult = "halted"
        else moveResult = (target == 'notarget').toString()
        var answer       = { 'endmove' : moveResult , 'move' : moveTodo }  //JSON obj
        const answerJson = JSON.stringify(answer)
        console.log('WebpageServer | doMove  answer= ' + answerJson  );
        target           = "notarget"; 	//reset target
        moveStillRunning = false;       //able to accept other moves
        moveHalted       = false;       //able to halt next move
        if( res != null ){
    		res.writeHead(200, { 'Content-Type': 'text/json' });
    		res.statusCode=200
    		//give info about nocollision to the POST sender
            res.write( answerJson  );
            res.end();
        }
        //IN ANY CASE: update all the controls / observers
        updateObservers(answerJson)
    }, duration);
}


//Updates the mirrors
function execMoveOnAllConnectedScenes(moveTodo, moveTime){
    console.log('$$$ WebpageServer doMove |  updates the mirrors'   );
	Object.keys(sockets).forEach( key => sockets[key].emit(moveTodo, moveTime) );
}
//Updates the controls and the observers (Jan 2021)
function updateObservers(msgJson){
    console.log("WebpageServer | updates the controls: " + msgJson   );
	Object.keys(wssockets).forEach( key => wssockets[key].send( msgJson ) )
}

/*
-------------------------------------------------------------------------------------
Interact with clients over ws (controls that send commands or observers) Jan 2021
-------------------------------------------------------------------------------------
*/
function initWs(){
const wsServer  = new WebSocket.Server( { port: 8091 }  );   // { server: app.listen(8091) }

wsServer.on('connection', (ws) => {
  wssocketIndex++
  console.log("$$$ WebpageServer wssocket | client connected wssocketIndex=" + wssocketIndex)
  const key      = wssocketIndex
  wssockets[key] = ws

  ws.on('message', msg => {
    console.log("$$$ WebpageServer wssocket |  wssocketIndex=" +  wssocketIndex + " received: "  )
	console.log( msg )
	var moveTodo = JSON.parse(msg).robotmove
	var duration = JSON.parse(msg).time
	if( moveStillRunning && moveTodo != "alarm"){
	    const answer  = { 'endmove' : "notallowed" , 'move' : moveTodo }
	    updateObservers( JSON.stringify(answer) )
	    return
	}
	if( moveTodo == "alarm" ){
	    execMoveOnAllConnectedScenes(moveTodo, duration)
	    moveHalted = true
	    return
	}
	 moveStillRunning=true
	 doMove(moveTodo, duration)
  });

  ws.onerror = (error) => {
	  console.log("$$$ WebpageServer wssocket | error: ${error}")
	  delete wssockets[key];
	  wssocketIndex--
	  console.log( "$$$ WebpageServer wssocket | disconnect wssocketIndex=" +  wssocketIndex )
  }

  ws.on('close', ()=>{
	  delete wssockets[key];
	  wssocketIndex--
	  console.log( "$$$ WebpageServer wssocket | disconnect wssocketIndex=" +  wssocketIndex )
  })
}); //wsServer.on('connection' ...
}//initWs
/*
-------------------------------------------------------------------------------------
Interact with the MASTER (the mirrors do not send any info)
-------------------------------------------------------------------------------------
*/
function initSocketIOWebGLScene() {
	console.log("WebpageServer WebGLScene |  socketIndex="+socketIndex)
    socketIO.on('connection', socket => {
        socketIndex++
        console.log("WebpageServer WebGLScene  | connection socketIndex="+socketIndex)
        const key    = socketIndex
        sockets[key] = socket
        if( socketIndex == 0) console.log("WebpageServer WebGLScene | MASTER-webpage ready")

		socket.on( 'sonarActivated', (obj) => {  //Obj is a JSON object
			console.log( "&&& WebpageServer WebGLScene | sonarActivated " );
			console.log(obj) 
			updateObservers( JSON.stringify(obj) )
		})
        socket.on( 'collision',     (obj) => { 
		    console.log( "WebpageServer WebGLScene  | collision detected " + obj + " numOfSockets=" + Object.keys(sockets).length );
		    target = obj;
		    const info     = { 'collision' : true, 'move': 'unknown'}
		    updateObservers( JSON.stringify(info) )
 		} )
        socket.on( 'disconnect',     () => { 
        		delete sockets[key];
          		socketIndex--;
			    alreadyConnected = ( socketIndex == 0 )
        		console.log("WebpageServer WebGLScene  | disconnect socketIndex="+socketIndex)
        	})
    })
}//initSocketIOWebGLScene

function startServer() {
    initSocketIOWebGLScene()
    initWs()
    hhtpSrv.listen(serverPort)
}
//MAIN
startServer()

