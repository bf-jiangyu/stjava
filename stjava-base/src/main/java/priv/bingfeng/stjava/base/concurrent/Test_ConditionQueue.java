package priv.bingfeng.stjava.base.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test_ConditionQueue {

    private static List<Object> list = new ArrayList<>();

    private static Lock lock = new ReentrantLock();

    private Condition putCondition = lock.newCondition();
    private Condition takeCondition = lock.newCondition();

    private int length;

    public Test_ConditionQueue(int length) {
        this.length = length;
    }

    public void put(Object obj) {
        lock.lock();
        try {
            for (;;) {
                if (list.size() < length) {
                    list.add(obj);
                    System.out.println("put obj");

                    takeCondition.signal();
                    break;
                } else {
                    putCondition.await();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Object take() {
        lock.lock();
        Object obj = null;
        try {
            for (;;) {
                if (list.size() > 0) {
                    obj = list.get(0);
                    list.remove(0);
                    System.out.println("take obj");

                    putCondition.signal();
                    break;
                } else {
                    takeCondition.await();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return obj;
    }

}
