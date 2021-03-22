/*
===============================================================
ClientBoundaryUsingPost.java
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
USES IssProtoclConfig.txt and IssRobotConfig.txt
USES the aril robot move language

Synchronous REQUEST-RESPONSE used in a doBoundary operation
that implements the business logic in a functional style
based on recursive functions.

The computation works in 1 thread
<<<<<<< HEAD
>>>>>>> moverobot
=======
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
===============================================================
*/
package it.unibo.wenv;
import it.unibo.annotations.*;
import it.unibo.interaction.*;
import it.unibo.supports.RobotApplicationStarter;

@ArilRobotSpec
public class ClientBoundaryUsingPost {
	private IssOperations rs;	//robot support

	public ClientBoundaryUsingPost(IssOperations support){  //Injected by UniboRobotApplicationStarter
		this.rs = support;
	}

	protected String doBoundary(int stepNum, String journey) {
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
		return doBoundary(stepNum + 1, journey + "l");
	}
/*
MAIN
 */
	public static void main(String[] args)   {
<<<<<<< HEAD
<<<<<<< HEAD
		Object appl = RobotApplicationStarter.createInstance(ClientBoundaryUsingPost.class);

		if( appl != null ) {
			String trip = ((ClientBoundaryUsingPost)appl).doBoundary(1,"");
			System.out.println("trip="+trip);
		}

=======
		System.out.println("ClientBoundaryUsingPost | main start n_Threads=" + Thread.activeCount());
		Object appl = RobotApplicationStarter.createInstance(ClientBoundaryUsingPost.class);
		System.out.println("ClientBoundaryUsingPost | appl  n_Threads=" + Thread.activeCount());
		String trip = ((ClientBoundaryUsingPost)appl).doBoundary(1,"");
		System.out.println("trip="+trip);
>>>>>>> moverobot
=======
		System.out.println("ClientBoundaryUsingPost | main start n_Threads=" + Thread.activeCount());
		Object appl = RobotApplicationStarter.createInstance(ClientBoundaryUsingPost.class);
		System.out.println("ClientBoundaryUsingPost | appl  n_Threads=" + Thread.activeCount());
		String trip = ((ClientBoundaryUsingPost)appl).doBoundary(1,"");
		System.out.println("trip="+trip);
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
	}
	
 }

