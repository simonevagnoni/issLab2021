/*
===============================================================
ClientBoundaryWebsockBasicAsynch.java
Use the cril language and the support specified in the
configuration file WebsocketBasicConfig.txt

GOAL: use request instead of requestSynch
and handle the information sent by WEnv over the cmdSocket-8091
===============================================================
*/
package it.unibo.wenv;
import it.unibo.annotations.IssProtocolSpec;
<<<<<<< HEAD
<<<<<<< HEAD
import it.unibo.interaction.IssObserver;
import it.unibo.interaction.IssOperations;
=======
import it.unibo.supports.IssCommSupport;
import it.unibo.interaction.IssObserver;
>>>>>>> moverobot
=======
import it.unibo.supports.IssCommSupport;
import it.unibo.interaction.IssObserver;
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
import it.unibo.supports.IssCommsSupportFactory;

@IssProtocolSpec( configFile ="WebsocketBasicConfig.txt" )
public class ClientBoundaryWebsockBasicAsynch {
<<<<<<< HEAD
<<<<<<< HEAD
    private IssOperations support;
    private IssObserver   controller;
    //Factory method
=======
    private IssCommSupport support; //The IssCommSupport is required in order to add observers and close it
    private IssObserver    controller;
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632

    //Factory method
    public static ClientBoundaryWebsockBasicAsynch createAndRun(){
        ClientBoundaryWebsockBasicAsynch obj = new ClientBoundaryWebsockBasicAsynch();
        IssCommSupport support               = new IssCommsSupportFactory().create( obj  );
        obj.setCommSupport(support);    //inject
        obj.controller = new RobotControllerBoundary(support, false);
        support.registerObserver( obj.controller );
        //support.registerObserver( new RobotObserver() );    //ANOTHER OBSERVER
        return obj;
    }

<<<<<<< HEAD
    protected void setCommSupport(IssOperations support){
=======
    private IssCommSupport support; //The IssCommSupport is required in order to add observers and close it
    private IssObserver    controller;

    //Factory method
    public static ClientBoundaryWebsockBasicAsynch createAndRun(){
        ClientBoundaryWebsockBasicAsynch obj = new ClientBoundaryWebsockBasicAsynch();
        IssCommSupport support                = new IssCommsSupportFactory().create( obj  );
        obj.setCommSupport(support);
        obj.controller = new RobotControllerBoundary(support);
        support.registerObserver( obj.controller );
        //support.registerObserver( new RobotObserver() );    //ANOTHER OBSERVER
        return obj;
    }

    protected void setCommSupport(IssCommSupport support){
>>>>>>> moverobot
=======
    protected void setCommSupport(IssCommSupport support){
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
        this.support = support;
    }

    public static void main(String args[]){
<<<<<<< HEAD
<<<<<<< HEAD
        ClientBoundaryWebsockBasicAsynch appl = ClientBoundaryWebsockBasicAsynch.createAndRun();
=======
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
        try {
            System.out.println("ClientBoundaryWebsockBasicAsynch | main start n_Threads=" + Thread.activeCount());
            ClientBoundaryWebsockBasicAsynch appl = ClientBoundaryWebsockBasicAsynch.createAndRun();
            RobotControllerBoundary ctrl = (RobotControllerBoundary) appl.controller;
<<<<<<< HEAD
            ctrl.start();
            Thread.sleep(30000);
=======
        try {
            System.out.println("ClientBoundaryWebsockBasicAsynch | main start n_Threads=" + Thread.activeCount());
            ClientBoundaryWebsockBasicAsynch appl = ClientBoundaryWebsockBasicAsynch.createAndRun();
            RobotControllerBoundary ctrl = (RobotControllerBoundary) appl.controller;
=======
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
            System.out.println("ClientBoundaryWebsockBasicSynch | appl n_Threads=" + Thread.activeCount());
            String trip = ctrl.doBoundary();       //wait until completion
            System.out.println("ClientBoundaryWebsockBasicAsynch | trip=" + trip  );
            System.out.println("ClientBoundaryWebsockBasicAsynch | main end n_Threads=" + Thread.activeCount());
<<<<<<< HEAD
>>>>>>> moverobot
=======
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }
}
