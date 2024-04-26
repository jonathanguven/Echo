package echo;

import java.net.Socket;
import java.util.HashMap;

public class ProxyHandler extends RequestHandler {

    protected Correspondent peer;
    private SafeTable<String, String> cache;
    private HashMap<String, String> users;
    private boolean loggedIn;


    public ProxyHandler(Socket s, SafeTable<String, String> cache) {
        super(s);
        this.cache = cache;
        users = new HashMap<>();
        loggedIn = false;
    }
    public ProxyHandler() { super(); }

    public void initPeer(String host, int port) {
        peer = new Correspondent();
        peer.requestConnection(host, port);
    }

    protected String response(String msg) throws Exception {
        String[] tokens = msg.split(" ");
        String command = tokens[0];

        switch (command) {
            case "new":
                if (tokens.length != 3) {
                    return "Invalid request format. Usage: new <user> <password>";
                }
                String newUser = tokens[1];
                String newPassword = tokens[2];
                if (users.containsKey(newUser)) {
                    return "User already exists.";
                }
                users.put(newUser, newPassword);
                return "User created successfully.";
            case "login":
                if (tokens.length != 3) {
                    return "Invalid request format. Usage: login <user> <password>";
                }
                String user = tokens[1];
                String password = tokens[2];
                if (!users.containsKey(user) || !users.get(user).equals(password)) {
                    return "Username or password is incorrect.";
                }
                System.out.println(user + " logged in successfully");
                loggedIn = true;
                return "Logged in successfully.";
            default:
                // forward msg to peer
                // return peer's response
                if (!loggedIn) {
                    return "You must be logged in to use this service.";
                }
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
}
