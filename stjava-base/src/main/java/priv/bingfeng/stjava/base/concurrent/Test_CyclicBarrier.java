package priv.bingfeng.stjava.base.concurrent;

public class Test_CyclicBarrier {

    public static void main(String[] args) {
//        CyclicBarrier barrier = new CyclicBarrier(4, () -> System.out.println("=====>>>达到四个"));
        Demo_CyclicBarrier barrier = new Demo_CyclicBarrier(4);

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    barrier.await();
                    System.out.println("上到摩天轮");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
