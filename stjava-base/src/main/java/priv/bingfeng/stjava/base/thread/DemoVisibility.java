package priv.bingfeng.stjava.base.thread;

public class DemoVisibility {

    private boolean isRunning = true;
    private int i = 0;

    public static void main(String[] args) {
        DemoVisibility demo = new DemoVisibility();
        new Thread(() -> {
            System.out.println("start");
            while (demo.isRunning) {
                demo.i ++;
            }
            System.out.println(demo.i);
        }).start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        demo.isRunning = false;
    }

}
