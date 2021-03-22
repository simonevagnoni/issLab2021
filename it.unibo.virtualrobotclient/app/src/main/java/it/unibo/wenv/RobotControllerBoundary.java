/*
===============================================================
<<<<<<< HEAD
<<<<<<< HEAD
RobotObserver.java
handles messages received on the cmdsocket-8091
=======
RobotControllerBoundary.java
implements the business logic by handling messages received on the cmdsocket-8091

>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
===============================================================
*/
package it.unibo.wenv;
import it.unibo.supports.IssCommSupport;
import it.unibo.interaction.IssObserver;
import it.unibo.interaction.MsgRobotUtil;
import org.json.JSONObject;

public class RobotControllerBoundary implements IssObserver {
private int stepNum              = 1;
private String journey           = "";
private boolean boundaryWalkDone = false ;
private IssCommSupport rs ;
private boolean usearil  = false;
private int moveInterval = 1000;

    //public enum robotLang {cril, aril}    //todo

    public RobotControllerBoundary(IssCommSupport support, boolean usearil){
        rs = support;
        this.usearil = usearil;
    }

<<<<<<< HEAD
    public void start(){
        doRobotAsynchMove( MsgRobotUtil.forwardMsg );
=======
RobotControllerBoundary.java
implements the business logic by handling messages received on the cmdsocket-8091

===============================================================
*/
package it.unibo.wenv;
import it.unibo.supports.IssCommSupport;
import it.unibo.interaction.IssObserver;
import it.unibo.interaction.MsgRobotUtil;
import it.unibo.supports.RobotSupport;
import org.json.JSONObject;

public class RobotControllerBoundary implements IssObserver {
private int stepNum              = 1;
private String journey           = "";
private boolean boundaryWalkDone = false ;
private RobotSupport rs ;

    public RobotControllerBoundary(IssCommSupport support){
        rs = new RobotSupport(support);
     }

    public synchronized String doBoundary(){
        rs.request( MsgRobotUtil.forwardMsg  );
=======
    //used by the main program
    public synchronized String doBoundary(){
        System.out.println("RobotControllerBoundary | doBoundary rs=" + rs + " usearil=" + usearil);
        rs.request( usearil ? MsgRobotUtil.wMsg : MsgRobotUtil.forwardMsg  );
        /*The reply to the request is sent by WEnv after the wtime defined in issRobotConfig.txt */
        delay(moveInterval ); //to reduce the robot move rate
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
        while( ! boundaryWalkDone ) {
            try {
                wait();
                //System.out.println("RobotControllerBoundary | RESUMES - final journey=" + journey);
                rs.close();
<<<<<<< HEAD
                return journey;
=======
                //return journey;
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return journey;
<<<<<<< HEAD
>>>>>>> moverobot
=======
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
    }

    @Override
    public void handleInfo(String infoJson) {
        handleInfo( new JSONObject(infoJson) );
    }

    /*
Handler of the messages sent by WENv over the cmdsocket-8091 to notify:
- the answer to a robot-command move {"endmove":"RESULT", "move":MOVE}
- the information emitted by a sonar { "sonarName": "sonarName", "distance": 1, "axis": "x" }
- a collision between the robot and an obstacle { "collision" : "false", "move": "moveForward"}
     */
    @Override
    public void handleInfo(JSONObject infoJson) {
        if( infoJson.has("endmove") )        handleEndMove(infoJson);
        else if( infoJson.has("sonarName") ) handleSonar(infoJson);
        else if( infoJson.has("collision") ) handleCollision(infoJson);
    }

    protected void handleSonar( JSONObject sonarinfo ){
        String sonarname = (String)  sonarinfo.get("sonarName");
        int distance     = (Integer) sonarinfo.get("distance");
        System.out.println("RobotControllerBoundary | handleSonar:" + sonarname + " distance=" + distance);
    }
    protected void handleCollision( JSONObject collisioninfo ){
        //we should handle a collision  when there are moving obstacles
        //in this case we could have a collision even if the robot does not move
        //String move   = (String) collisioninfo.get("move");
        //System.out.println("RobotControllerBoundary | handleCollision move=" + move  );
    }
    protected void handleEndMove(JSONObject endmove ){
        String answer = (String) endmove.get("endmove");
        String move   = (String) endmove.get("move");
        //System.out.println("RobotControllerBoundary | handleEndMove:" + move + " answer=" + answer);
        switch( answer ){
            case "true"       : boundary( move, false );break;
            case "false"      : boundary( move, true  );break;
            case "halted"     : System.out.println("RobotControllerBoundary | handleEndMove to do halt" );break;
            case "notallowed" : System.out.println("RobotControllerBoundary | handleEndMove to do notallowed" );break;
            default           : System.out.println("RobotControllerBoundary | handleEndMove IMPOSSIBLE answer for move=" + move);
        }
    }
<<<<<<< HEAD
<<<<<<< HEAD
//Business logic
    protected void boundary( JSONObject mv ){
=======
//Business logic in RobotControllerBoundary
    protected synchronized void boundary( JSONObject mv ){
>>>>>>> moverobot
        String answer = (String) mv.get("endmove");
        String move   = (String) mv.get("move");
        System.out.println("RobotControllerBoundary | boundary stepNum:" + stepNum + " " + journey);
        if (stepNum <= 4) {
            if( move.equals("turnLeft") ){
                journey = journey + "l";
<<<<<<< HEAD
                stepNum++;
                doRobotAsynchMove( MsgRobotUtil.forwardMsg );
=======
                if (stepNum == 4) { boundaryWalkDone=true; notify(); return; }
                stepNum++;
                rs.request(  MsgRobotUtil.forwardMsg );
>>>>>>> moverobot
=======
//Business logic in RobotControllerBoundary
    protected synchronized void boundary( String move, boolean obstacle ){
         if (stepNum <= 4) {
            if( move.equals("turnLeft") ){
                journey = journey + "l";
                if (stepNum == 4) {
                    boundaryWalkDone=true;
                    notify(); //to resume the main
                    return;
                }
                stepNum++;
                rs.request( usearil ? MsgRobotUtil.wMsg : MsgRobotUtil.forwardMsg );
                delay(moveInterval ); //to reduce the robot move rate
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
                return;
            }
            //the move is moveForward
            if( obstacle ){
                rs.request( usearil ? MsgRobotUtil.lMsg : MsgRobotUtil.turnLeftMsg   );
            }
            if( ! obstacle ){
                journey = journey + "w";
<<<<<<< HEAD
<<<<<<< HEAD
                doRobotAsynchMove( MsgRobotUtil.forwardMsg );
            }else{ //collision
                doRobotAsynchMove(MsgRobotUtil.turnLeftMsg);
=======
                rs.request( MsgRobotUtil.forwardMsg );
            }else{ //collision
                rs.request(MsgRobotUtil.turnLeftMsg );
>>>>>>> moverobot
=======
                rs.request( usearil ? MsgRobotUtil.wMsg : MsgRobotUtil.forwardMsg );
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
            }
            delay(moveInterval ); //to reduce the robot move rate
        }else{ //stepNum > 4
            System.out.println("RobotControllerBoundary | boundary journey:" + journey);
        }
    }

<<<<<<< HEAD
<<<<<<< HEAD
//Utility
    protected void doRobotAsynchMove(String jsonMoveStr) {
        System.out.println(jsonMoveStr);
        //"{\"robotmove\":\"...\", \"time\": ...}";
        JSONObject jsonObj = new JSONObject(jsonMoveStr);
        int time = Integer.parseInt( jsonObj.get("time").toString() );
        rs.forward( jsonMoveStr );
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        //The answer is handled by the controllers
    }


=======
>>>>>>> moverobot
=======
    protected void delay( int dt ){
        try { Thread.sleep(dt); } catch (InterruptedException e) { e.printStackTrace(); }
    }

>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
}
