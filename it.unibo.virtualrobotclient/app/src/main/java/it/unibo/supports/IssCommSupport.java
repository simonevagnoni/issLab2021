/**
 IssCommSupport.java
 ==========================================================================

 ==========================================================================
 */
package it.unibo.supports;
<<<<<<< HEAD

=======
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
import it.unibo.interaction.IssObserver;
import it.unibo.interaction.IssOperations;

public interface IssCommSupport extends IssOperations {
    void registerObserver( IssObserver obs );
    void removeObserver( IssObserver obs );
    void close();
}
