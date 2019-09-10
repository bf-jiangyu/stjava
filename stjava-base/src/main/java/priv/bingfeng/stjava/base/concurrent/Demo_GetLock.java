package priv.bingfeng.stjava.base.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo_GetLock {

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        lock.lock();

        Thread t1 = new Thread(() -> {
            boolean b = lock.tryLock();
            System.out.println("获得锁:" + b);

            try {
                boolean c = lock.tryLock(5, TimeUnit.SECONDS);
                System.out.println("等待5s获取锁:" + c);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("interrupted");
            }

            lock.lock();
            System.out.println("获得锁");
        });
        t1.start();

        Thread.sleep(10000);

        t1.interrupt();

        Thread.sleep(3000);
        System.out.println("释放锁");
        lock.unlock();
    }

}
