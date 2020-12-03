package com.test;

import com.main.CustomReentrantLock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomReentrantLockTest {
    @Test
    public void testOnWorkers() throws InterruptedException {
        CustomReentrantLock lock = new CustomReentrantLock();
        Counter counter = new Counter(0);
        final int numOfWorkers = 3;
        Thread[] threads = new Thread[numOfWorkers];

        for (int i = 0; i < numOfWorkers; i++) {
            threads[i] = new Thread(new IncrementRunnable(lock, counter));
        }
        for (int i = 0; i < numOfWorkers; i++) {
            threads[i].start();
        }
        Thread.sleep(50);
        Assertions.assertEquals(IncrementRunnable.NUM_OF_INCREMENTS * numOfWorkers, counter.getCounter());
    }

    @Test
    public void testHoldCount() {
        CustomReentrantLock lock = new CustomReentrantLock();
        lock.lock();
        lock.lock();
        Assertions.assertEquals(2, lock.getHoldCount());
        lock.unlock();
        Assertions.assertEquals(1, lock.getHoldCount());
    }
}
