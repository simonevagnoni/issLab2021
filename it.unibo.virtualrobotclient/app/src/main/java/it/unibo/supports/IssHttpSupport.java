/**
 IssHttpSupport.java
 ===============================================================
 Support for HTTP interaction with a remote server
 The correct format of the arguments of operations forward/request
 must be provided by the user
 ===============================================================
 */
package it.unibo.supports;

import it.unibo.interaction.IssObserver;
<<<<<<< HEAD
<<<<<<< HEAD
import it.unibo.interaction.IssOperations;
=======
>>>>>>> moverobot
=======
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
<<<<<<< HEAD
<<<<<<< HEAD

import java.net.URI;

public class IssHttpSupport implements IssOperations {
=======
import java.net.URI;

public class IssHttpSupport implements IssCommSupport {
>>>>>>> moverobot
=======
import java.net.URI;

public class IssHttpSupport implements IssCommSupport {
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
    private CloseableHttpClient httpclient;
    private  String URL  = "unknown";

    public IssHttpSupport(String url ){
        httpclient = HttpClients.createDefault();
        URL        = url;
<<<<<<< HEAD
<<<<<<< HEAD
        System.out.println( "        IssHttpSupport | created IssHttpSupport url=" + url  );
=======
        System.out.println("        IssHttpSupport | created IssHttpSupport url=" + url  );
        //System.out.println("        IssHttpSupport |  n_Threads=" + Thread.activeCount());
>>>>>>> moverobot
=======
        System.out.println("        IssHttpSupport | created IssHttpSupport url=" + url  );
        //System.out.println("        IssHttpSupport |  n_Threads=" + Thread.activeCount());
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
    }

    @Override
    public void forward( String msg)  {
        System.out.println( "        IssHttpSupport | forward:" + msg  );
        performrequest(msg);
    }

    @Override
    public void request( String msg) {
        System.out.println( "        IssHttpSupport | request:" + msg  );
        performrequest(msg);    //the answer is lost
    }

    @Override
    public void reply(String msg) {
        System.out.println( "        IssHttpSupport | WARNING: reply NOT IMPLEMENTED"  );
    }

    @Override
    public String requestSynch( String msg) {
        //System.out.println( "        IssHttpSupport | requestSynch:" + msg  );
        return performrequest(msg);    //the answer is lost
    }

    @Override
    public void registerObserver( IssObserver obs ){
        //TODO
    }
    @Override
    public void removeObserver( IssObserver obs ){
        //TODO
    }
<<<<<<< HEAD
<<<<<<< HEAD

=======
=======
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
    @Override
    public void close(){
        try {
            httpclient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
<<<<<<< HEAD
>>>>>>> moverobot
=======
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
 //===================================================================

    protected String performrequest( String msg )  {
        boolean endmove = false;
        try {
            //System.out.println( "        IssHttpSupport | performrequest:" + msg + " URL=" + URL );
            StringEntity entity     = new StringEntity(msg);
            HttpUriRequest httppost = RequestBuilder.post()
                    .setUri(new URI(URL))
                    .setHeader("Content-Type", "application/json")
                    .setHeader("Accept", "application/json")
                    .setEntity(entity)
                    .build();
            CloseableHttpResponse response = httpclient.execute(httppost);
            //System.out.println( "IssHttpSupport | response:" + response  );
            String jsonStr = EntityUtils.toString( response.getEntity() );
<<<<<<< HEAD
<<<<<<< HEAD
            JSONObject jsonObj = new JSONObject(jsonStr) ;
            if( jsonObj.get("endmove") != null ) {
                endmove = jsonObj.getBoolean("endmove");
                //System.out.println("IssHttpSupport | response=" + endmove);
=======
=======
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
            JSONObject jsonEndmove = new JSONObject(jsonStr) ;
            //System.out.println("IssHttpSupport | jsonEndmove=" + jsonEndmove);
            if( jsonEndmove.get("endmove") != null ) {
                endmove = jsonEndmove.getBoolean("endmove");
<<<<<<< HEAD
>>>>>>> moverobot
=======
>>>>>>> c93bbbc933d90211548af7f07499f9d1df487632
            }
        } catch(Exception e){
            System.out.println("        IssHttpSupport | ERROR:" + e.getMessage());
         }
        return ""+endmove;
    }

}
