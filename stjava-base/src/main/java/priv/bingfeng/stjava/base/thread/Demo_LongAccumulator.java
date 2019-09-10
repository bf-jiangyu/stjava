package priv.bingfeng.stjava.base.thread;

import java.util.concurrent.atomic.LongAccumulator;

public class Demo_LongAccumulator {

    public static void main(String[] args) {
        LongAccumulator accumulator = new LongAccumulator((x, y) -> {
            System.out.println("x:" + x + ", y:" + y);
            return x + y;
        }, 0L);

        for (int i = 0; i < 3; i++) {
            accumulator.accumulate(i + 1);
        }

        System.out.println("result:" + accumulator.get());
    }

}
