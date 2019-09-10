package priv.bingfeng.stjava.base.concurrent;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Test_CountDownLatch {

    private Sync sync;

    public Test_CountDownLatch(int count) {
        sync = new Sync(count);
    }

    public void countDown() {
        sync.releaseShared(1);
    }

    public void await() {
        sync.acquireShared(1);
    }

    class Sync extends AbstractQueuedSynchronizer {

        public Sync(int count) {
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            return getState() == 0 ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for (;;) {
                int c = getState();
                if (c == 0) return false;
                int nextc = c - 1;
                if  (compareAndSetState(c, nextc)) {
                    return nextc == 0;
                }
            }
        }
    }

}
