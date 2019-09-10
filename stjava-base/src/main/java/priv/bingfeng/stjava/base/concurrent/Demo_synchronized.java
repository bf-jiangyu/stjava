package priv.bingfeng.stjava.base.concurrent;

public class Demo_synchronized {

    private static synchronized void test() {
        System.out.println("run");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        test();
    }

    public static void main(String[] args) {
        test();
    }

}
