package priv.bingfeng.stjava.base.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Demo_ReadWriteLock {

    private final Map<String, Object> map = new HashMap<>();

    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private Lock w = rwl.writeLock();

    public Object get(String key) {
        r.lock();
        try {
            return map.get(key);
        } finally {
            r.unlock();
        }
    }

    public Object[] allkeys() {
        r.lock();
        try {
            return map.keySet().toArray();
        } finally {
            r.unlock();
        }
    }

    public Object put(String key, Object obj) {
        w.lock();
        try {
            return map.put(key, obj);
        } finally {
            w.unlock();
        }
    }

    public void clear() {
        w.lock();
        try {
            map.clear();
        } finally {
            w.unlock();
        }
    }
}
