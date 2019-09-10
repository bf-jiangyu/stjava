package priv.bingfeng.stjava.base.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class Demo_FutureTask<T> implements Runnable {

    private volatile int state = NEW;
    private static final int NEW = 0;
    private static final int RUNNING = 1;
    private static final  int FINISED = 2;

    public Demo_FutureTask(Callable<T> task) {
        this.callable = task;
    }

    private T result;

    Callable<T> callable;

    LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>(100);

    AtomicReference<Thread> runner = new AtomicReference<>();

    @Override
    public void run() {
        if (state != NEW || !runner.compareAndSet(null, Thread.currentThread())) {
            return;
        }
        state = RUNNING;

        try {
            result = callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            state = FINISED;
        }

        while (true) {
            Thread waiter = waiters.poll();
            if (waiter == null) break;
            LockSupport.unpark(waiter);
        }
    }


    public T get() {
        if (state != FINISED) {
            waiters.offer(Thread.currentThread());
        }

        while (state != FINISED) {
            LockSupport.park();
        }

        return result;
    }

}
