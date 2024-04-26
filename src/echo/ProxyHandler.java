package echo;

import java.net.Socket;
import java.util.HashMap;

public class ProxyHandler extends RequestHandler {

    protected Correspondent peer;


    public ProxyHandler(Socket s) {
        super(s);
    }
    public ProxyHandler() { super(); }

    public void initPeer(String host, int port) {
        peer = new Correspondent();
        peer.requestConnection(host, port);
    }

    protected String response(String msg) throws Exception {
        System.out.println("ProxyHandler received: " + msg);
        peer.send(msg);
        return peer.receive();
    }
}
