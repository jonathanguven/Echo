package echo;

import java.net.Socket;
import java.util.HashMap;

public class CacheHandler extends ProxyHandler {

    private SafeTable<String, String> cache;


    public CacheHandler(Socket s) {
        super(s);
        this.cache = new SafeTable<>();
    }

    @Override
    protected String response(String msg) throws Exception {
        if (cache.containsKey(msg)) {
            System.out.println("Cache hit for request: " + msg);
            return cache.get(msg);
        }
        String response = super.response(msg);

        cache.put(msg, response);
        System.out.println("Added response to cache for request: " + msg);
        return response;
    }
}
