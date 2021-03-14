/*
===============================================================
<<<<<<< HEAD
RobotObserver.java
handles messages received on the cmdsocket-8091
===============================================================
*/
package it.unibo.wenv;
import it.unibo.interaction.IssObserver;
import it.unibo.interaction.IssOperations;
import it.unibo.interaction.MsgRobotUtil;
import org.json.JSONObject;

public class RobotControllerBoundary implements IssObserver {
private int stepNum      = 1;
private String journey   = "";
private IssOperations rs ;

    public RobotControllerBoundary(IssOperations support){
        rs = support;
    }

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
        while( ! boundaryWalkDone ) {
            try {
                wait();
                //System.out.println("RobotControllerBoundary | RESUMES - final journey=" + journey);
                rs.close();
                return journey;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return journey;
>>>>>>> moverobot
    }

    @Override
    public void handleInfo(String infoJson) {
        handleInfo( new JSONObject(infoJson) );
    }
    @Override
    public void handleInfo(JSONObject infoJson) {
        if( infoJson.has("endmove") ) boundary(infoJson);
    }
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
                return;
            }
            if( move.equals("moveForward") && answer.equals("true") ){
                journey = journey + "w";
<<<<<<< HEAD
                doRobotAsynchMove( MsgRobotUtil.forwardMsg );
            }else{ //collision
                doRobotAsynchMove(MsgRobotUtil.turnLeftMsg);
=======
                rs.request( MsgRobotUtil.forwardMsg );
            }else{ //collision
                rs.request(MsgRobotUtil.turnLeftMsg );
>>>>>>> moverobot
            }
        }else{ //steps ended
            System.out.println("RobotControllerBoundary | boundary journey:" + journey);
        }
    }

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
}
