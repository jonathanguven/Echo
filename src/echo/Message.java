package echo;

import java.io.Serializable;

public class Message<T extends Serializable> implements Serializable {
    T content;
    public Message(T content) {
        this.content = content;
    }

    public String toString() {
        return "[" + content + "]";
    }
}
