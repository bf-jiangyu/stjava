package priv.bingfeng.stjava.base.concurrent.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TaskBlockingQueue<T> {

    private T obj;

    private final ReentrantLock takeLock = new ReentrantLock();
    private final Condition notNull = takeLock.newCondition();

    private final ReentrantLock putLock = new ReentrantLock();
    private final Condition isNull = putLock.newCondition();

    private void signalNotNull() {
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            notNull.signal();
        } finally {
            takeLock.unlock();
        }
    }

    private void signalIsNull() {
        final ReentrantLock putLock = this.putLock;
        putLock.lock();
        try {
            isNull.signal();
        } finally {
            putLock.unlock();
        }
    }


    public void put(T inObj) throws InterruptedException {
        final ReentrantLock putLock = this.putLock;
        putLock.lockInterruptibly();
        try {
            while (obj != null) {
                isNull.await();
            }

            obj = inObj;

            signalNotNull();
            isNull.await();
        } finally {
            putLock.unlock();
        }
    }

    public T take() throws InterruptedException {
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lockInterruptibly();
        try {
            while (obj == null) {
                notNull.await();
            }
            return obj;
        } finally {
            takeLock.unlock();
            obj = null;
            signalIsNull();
        }
    }

    public T take(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);

        final ReentrantLock takeLock = this.takeLock;
        takeLock.lockInterruptibly();
        try {
            while (obj == null) {
                if (nanos <= 0)
                    return null;
                nanos = notNull.awaitNanos(nanos);
            }
            return obj;
        } finally {
            takeLock.unlock();
            if (nanos > 0) {
                obj = null;
                signalIsNull();
            }
        }
    }

}
