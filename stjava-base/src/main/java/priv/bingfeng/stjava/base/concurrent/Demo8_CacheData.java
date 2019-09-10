package priv.bingfeng.stjava.base.concurrent;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Demo8_CacheData {

}

class TeacherInfoCache {

    static volatile boolean cacheValid;

    static final ReadWriteLock rwl = new ReentrantReadWriteLock();

    static Object get(String dataKey) {
        Object data = null;
        rwl.readLock().lock();
        try {
            if (cacheValid) {
//                data = ;
            } else {
                rwl.readLock().unlock();
                rwl.writeLock().lock();

                try {
                    if (!cacheValid) {
//                        data = Database.queryUserInfo();
//                        Redis.data.put(dataKey, data);
                        cacheValid = true;

                    }
                } finally {
                    rwl.readLock().lock();
                    rwl.writeLock().unlock();
                }
//                data = Database.queryUserInfo();
//                Redis.data.put(dataKey, data);
            }
            return data;
        } finally {
            rwl.readLock().unlock();
        }
    }
}
