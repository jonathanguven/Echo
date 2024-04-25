package math;

import java.net.*;
import echo.*;

public class MathHandler extends RequestHandler {

    public MathHandler(Socket sock) {
        super(sock);
    }

    public MathHandler() {
        super();
    }

    protected String response(String request) throws Exception {
        String[] parts = request.split("\\s+");
        if (parts.length < 3) {
            return "Incorrect usage: Format must be <operator> <number> <number> etc.";
        }
        String operator = parts[0];
        double result;
        try {
            result = Double.parseDouble(parts[1]);
        } catch (NumberFormatException e) {
            return "Invalid format";
        }
        for (int i = 2; i < parts.length; i++) {
            double num = Double.parseDouble(parts[i]);
            switch (operator) {
                case "add":
                    result += num;
                    break;
                case "sub":
                    result -= num;
                    break;
                case "mul":
                    result *= num;
                    break;
                case "div":
                    if (num == 0) {
                        return "Cannot divide by zero";
                    }
                    result /= num;
                    break;
                default:
                    return "Invalid operator. Must be add, sub, mul, or div.";
            }
        }
        return String.format("%.1f", result);
    }
}
