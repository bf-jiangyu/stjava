package priv.bingfeng.stjava.base.thread;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class DemoAtomic {

    private void testCounter() {
        CountDownLatch downLatch = new CountDownLatch(6);

        Counter ct = new Counter();
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < 10000; i1++) {
                    ct.add();
                }
                System.out.println("done...");
                downLatch.countDown();
            }).start();
        }

        try {
            downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(ct.i);
    }

    private void testAtomicInt() {
        CountDownLatch downLatch = new CountDownLatch(6);

        AtomicInteger ct = new AtomicInteger();
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < 10000; i1++) {
                    ct.incrementAndGet();
                }
                System.out.println("done...");
                downLatch.countDown();
            }).start();
        }

        try {
            downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(ct.get());
    }

    private static class Counter {
        volatile int i = 0;
        private static Unsafe unsafe;
        private static long valueOffset;
        static {
            try {
                Field field = Unsafe.class.getDeclaredField("theUnsafe");
                field.setAccessible(true);
                unsafe = (Unsafe) field.get(null);

                Field filed1 = Counter.class.getDeclaredField("i");
                valueOffset = unsafe.objectFieldOffset(filed1);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        void add() {
//            i++;
            for (;;) {
                int current = unsafe.getIntVolatile(this, valueOffset);
                if (unsafe.compareAndSwapInt(this, valueOffset, current, current + 1))
                    break;
            }
        }
    }

    public static void main(String[] args) {
        DemoAtomic demo = new DemoAtomic();
        demo.testCounter();
        demo.testAtomicInt();
    }

}
