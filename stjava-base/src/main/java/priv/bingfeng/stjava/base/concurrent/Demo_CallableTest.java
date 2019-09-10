package priv.bingfeng.stjava.base.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.LockSupport;

public class Demo_CallableTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallTask callTask = new CallTask();
//        FutureTask<String> task = new FutureTask<>(callTask);
        Demo_FutureTask<String> task = new Demo_FutureTask<>(callTask);

        new Thread(task).start();

        System.out.println("begin to get");
        String result = task.get();
        System.out.println(result);

        // 只会打印一次
        new Thread(task).start();
    }

}

class CallTask implements Callable<String> {
    @Override
    public String call() throws Exception {
        LockSupport.parkNanos(1000 * 1000 * 1000 * 5L);
        System.out.println("done...");
        return "james";
    }
}
