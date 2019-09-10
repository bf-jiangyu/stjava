package priv.bingfeng.stjava.base.thread;

import java.util.concurrent.*;

public class Test_FixedThreadPool {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.submit(new Task());
        pool.submit(new Task());
        pool.submit(new Task());

        ThreadPoolExecutor pool2 = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1));
        pool2.submit(new Task());
        pool2.submit(new Task());
        pool2.submit(new Task());
    }

    private static class Task implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(123);
        }
    }

}
