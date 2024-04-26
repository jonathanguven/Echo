package echo;

import java.net.Socket;

public class ProxyHandler extends RequestHandler {

    protected Correspondent peer;
    private SafeTable<String, String> cache;

    public ProxyHandler(Socket s, SafeTable<String, String> cache) {
        super(s);
        this.cache = cache;
    }
    public ProxyHandler() { super(); }

    public void initPeer(String host, int port) {
        peer = new Correspondent();
        peer.requestConnection(host, port);
    }

    protected String response(String msg) throws Exception {
        // forward msg to peer
        // return peer's response
        String cached = cache.get(msg);
        if (cached != null) {
            System.out.println("Cache hit: " + msg);
            return cached;
        }
        peer.send(msg);
        String response = peer.receive();

        cache.put(msg, response);
        System.out.println("Added to cache: " + msg);

        return response;
    }
}
