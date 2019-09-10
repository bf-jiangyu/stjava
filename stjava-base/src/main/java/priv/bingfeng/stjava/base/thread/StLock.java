package priv.bingfeng.stjava.base.thread;

public class StLock {

    private void test1() {
        Thread t1 = new Thread(() -> {
            System.out.println("ab");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cd");
        });
        System.out.println(t1.getState());
        t1.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(t1.getState());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t1.getState());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t1.getState());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t1.getState());

        while (t1.getState() != Thread.State.TERMINATED) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("end");
    }

    public static void main(String[] args) {
        StLock demo = new StLock();
        demo.test1();
    }

}
