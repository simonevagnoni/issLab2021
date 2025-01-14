/**
 IssOperations.java
 ==========================================================================
 Defines high-level interaction operation
 These operations are introduced with reference to message-passing
 rather than procedure-call.
 Thus, forward is just 'fire and forget', while
 request assumes that the called will execute a reply related to that request

 requestSynch is introduced to help the transition to the new paradigm,

 ==========================================================================
 */
package it.unibo.interaction;

public interface IssOperations {
    void forward( String msg ) ;  //String related to cril, aril, AppMsg
    void request( String msg );
    void reply( String msg );
<<<<<<< HEAD
<<<<<<< HEAD
    void registerObserver( IssObserver obs );
    void removeObserver( IssObserver obs );
=======
>>>>>>> moverobot
=======
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
    String requestSynch( String msg );
}
