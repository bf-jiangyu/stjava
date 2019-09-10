package priv.bingfeng.stjava.base.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Demo_CyclicBarrier {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    // 当前批次有多少个
    private int count = 0;

    // 记录批次大小
    private final int parties;

    // 分代
    private Object generation = new Object();

    public Demo_CyclicBarrier(int parties) {
        if (parties <= 0)
            throw new IllegalArgumentException();
        this.parties = parties;
    }

    public void nextGeneration() {
        condition.signalAll();
        count = 0;
        generation = new Object();
        System.out.println("");
        System.out.println("");
    }

    public void await() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Object g = generation;

            int index = ++count;
            if (index == parties) {
                nextGeneration();
                return;
            }

            for (;;) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (g != generation) {
                    return;
                }
            }
        } finally {
            lock.unlock();
        }
    }

}
