package com.test;

import com.main.CustomReentrantLock;

public class IncrementRunnable implements Runnable {
    public static final int NUM_OF_INCREMENTS = 10;
    private final CustomReentrantLock lock;
    private final Counter counter;

    public IncrementRunnable(CustomReentrantLock lock, Counter counter) {
        this.lock = lock;
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < NUM_OF_INCREMENTS; i++) {
            lock.lock();
            counter.increment();
        }
        for (int i = 0; i < NUM_OF_INCREMENTS; i++) {
            lock.unlock();
        }
    }
}
