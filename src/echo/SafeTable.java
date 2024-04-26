package echo;

import java.util.HashMap;

public class SafeTable<K, V> extends HashMap<K, V> {
    public synchronized V put(K key, V value) {
        return super.put(key, value);
    }

    public synchronized V get(Object key) {
        return super.get(key);
    }
}
