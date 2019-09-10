package priv.bingfeng.stjava.base.concurrent;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Demo_Semaphore {

    private Sync sync;

    public Demo_Semaphore(int permits) {
        sync = new  Sync(permits);
    }

    public void acquire() {
        sync.acquireShared(1);
    }

    public void release() {
        sync.releaseShared(1);
    }

    class Sync extends AbstractQueuedSynchronizer {

        private int permits;

        private Sync(int permits) {
            this.permits = permits;
        }

        @Override
        protected int tryAcquireShared(int arg) {
            int state = getState();
            int nextState = state + arg;
            if (nextState <= permits) {
                if (compareAndSetState(state, nextState)) {
                    return 1;
                }
            }
            return -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            int state = getState();

            if (compareAndSetState(state, state - arg)) {
                return true;
            } else {
                return false;
            }
        }
    }

}
