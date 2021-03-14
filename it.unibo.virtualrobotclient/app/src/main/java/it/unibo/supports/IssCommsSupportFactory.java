/**
 IssCommsFactory.java
 ===============================================================
 Creates a support that provides high-level IssOperations
 according to the annotation related to the given object
 or according to a protocol-configuration file
 ===============================================================
 */
package it.unibo.supports;
import it.unibo.annotations.IssAnnotationUtil;
<<<<<<< HEAD
import it.unibo.annotations.IssProtocolSpec;
import it.unibo.annotations.ProtocolInfo;
import it.unibo.interaction.IssOperations;

public class IssCommsSupportFactory {
    //Factory Method
    public static IssOperations create(Object obj ){
=======
import it.unibo.annotations.ProtocolInfo;

public class IssCommsSupportFactory {
    //Factory Method
    public static IssCommSupport create(Object obj ){   //obj must be properly annotated
>>>>>>> moverobot
        ProtocolInfo protocolInfo = IssAnnotationUtil.getProtocol(  obj );
        String protocol     = protocolInfo.getProtocol();
        String url          = protocolInfo.getUrl();
        return create(protocol,url);
    }
    //Factory Method
<<<<<<< HEAD
    public static IssOperations create(  String protocol, String url ){
=======
    public static IssCommSupport create(  String protocol, String url ){
>>>>>>> moverobot
         System.out.println("        IssCommsFactory | create protocol=" + protocol  );
        switch( protocol ){
            case "HTTP"  : {  return new IssHttpSupport(  url );  }
            case "WS"    : {  return new IssWsSupport( url );    }
            default: return new IssHttpSupport(  url ); //TODO Exception?
        }
    }
}

