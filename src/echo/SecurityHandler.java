package echo;

import java.net.Socket;
import java.util.HashMap;

public class SecurityHandler extends ProxyHandler {

    private SafeTable<String, String> users;
    private boolean loggedIn;


    public SecurityHandler(Socket s) {
        super(s);
        this.users = new SafeTable<>();
        this.loggedIn = false;
        System.out.println("SecurityHandler created");
    }

    public SecurityHandler() {
        super();
        this.users = new SafeTable<>();
        this.loggedIn = false;
        System.out.println("SecurityHandler created");
    }

    @Override
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
                if (!loggedIn) {
                    return "You must be logged in to use this service.";
                }
                return super.response(msg);
        }
    }
}
