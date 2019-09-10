package priv.bingfeng.stjava.base.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo_Condition {

    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("condition await");
                condition.await();
                System.out.println("here i am");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("condition unlock");
                lock.unlock();
            }
        }).start();

        Thread.sleep(3000);

        lock.lock();
        condition.signal();
        System.out.println("signal");
        Thread.sleep(5000);
        lock.unlock();

        lock.lock();
        System.out.println("end");
        lock.unlock();
    }

}
