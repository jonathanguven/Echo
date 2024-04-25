package echo;

import java.net.Socket;

public class RequestHandler extends Correspondent implements Runnable {
    protected boolean active; // response can be set to false to terminate thread
    public RequestHandler(Socket s) {
        super(s);
        active = true;
    }
    public RequestHandler() {
        super();
        active = true;
    }
    // override in a subclass:
    protected String response(String msg) throws Exception {
        return "echo: " + msg;
    }
    // any housekeeping can be done by an override of this:
    protected void shutDown() {
        if (Server.DEBUG) System.out.println("handler shutting down");
        active = false;
    }
    public void run() {
        while(active) {
            try {
                String request = receive();
                // receive request
                if(request.equals("quit")) {
                    shutDown();
                    break;
                }
                // send response
                // sleep
                String response = response(request);
                System.out.println("received: " + request);
                System.out.println("sending: " + response);
                send(response);
            } catch(Exception e) {
                send(e.getMessage() + "... ending session");
                break;
            }
        }
        // close
    }
}