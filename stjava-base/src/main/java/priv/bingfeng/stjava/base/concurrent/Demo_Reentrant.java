package priv.bingfeng.stjava.base.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo_Reentrant {

    private static int i = 0;
    private final static Lock lc = new ReentrantLock();

    public static void main(String[] args) {
        new Thread(() -> {
            lc.lock();
            lc.lock();
            System.out.println("thread1 print");
            lc.unlock();
            System.out.println("thread1 unlock");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lc.unlock();
            System.out.println("thread1 unlock 2");
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            lc.lock();
            System.out.println("thread2");
            lc.unlock();
        }).start();
    }

}
