package com.main;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomReentrantLock {
    static {
        new PropertyConfigurator().doConfigure("log4j.properties", LogManager.getLoggerRepository());
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomReentrantLock.class);

    private boolean isLocked = false;
    private Thread lockOwner = null;
    private int holdCount = 0;

    public int getHoldCount() {
        return holdCount;
    }

    public synchronized void lock() {
        Thread thisThread = Thread.currentThread();
        while (isLocked && lockOwner != thisThread) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                LOGGER.error("Waiting thread #" + Thread.currentThread().getId() + " was interrupted.", e);
            }
        }
        lockOwner = thisThread;
        holdCount++;
    }

    public synchronized void unlock() {
        if (lockOwner == Thread.currentThread()) {
            holdCount--;
            if (holdCount == 0) {
                lockOwner = null;
                isLocked = false;
                this.notify();
            }
        }
    }
}
