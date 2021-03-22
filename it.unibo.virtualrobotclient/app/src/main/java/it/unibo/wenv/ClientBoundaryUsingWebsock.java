/**
 * ClientBoundaryUsingWebsock
 */

package it.unibo.wenv;
import it.unibo.annotations.*;
import it.unibo.interaction.IssOperations;
import it.unibo.interaction.MsgRobotUtil;
import it.unibo.supports.RobotApplicationStarter;

@ArilRobotSpec
public class ClientBoundaryUsingWebsock {
    private IssOperations rs;	//robot support

    public ClientBoundaryUsingWebsock(IssOperations support){  //Injected by UniboRobotApplicationStarter
        this.rs = support;
    }
/*
BUSINESS LOGIC
 */
protected String doBoundary( ) {
<<<<<<< HEAD
<<<<<<< HEAD
    return RobotSupport.doBoundary( 1, "", rs);
=======
    return RobotSupport.doBoundarySynch( 1, "", rs);
>>>>>>> moverobot
=======
    return  doBoundarySynch( 1, "", rs);
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
}


    public static String doBoundarySynch(int stepNum, String journey, IssOperations rs) {
        if (stepNum > 4) {
            return journey;
        }
        String answer = rs.requestSynch( MsgRobotUtil.wMsg );
        while( answer.equals("true") ){
            journey = journey + "w";
            answer = rs.requestSynch( MsgRobotUtil.wMsg );
        }
        //collision
        rs.requestSynch(MsgRobotUtil.lMsg);
        return doBoundarySynch(stepNum + 1, journey + "l", rs);
    }
 /*
MAIN
 */
    public static void main(String[] args) {
        Object appl = RobotApplicationStarter.createInstance(ClientBoundaryUsingWebsock.class);

        if( appl != null ) {
            String trip =  ((ClientBoundaryUsingWebsock)appl).doBoundary();
            System.out.println("trip=" + trip);
        }
    }

}