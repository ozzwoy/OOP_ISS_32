package com.main;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

public class ThreadPoolRunnable implements Runnable {
    static {
        new PropertyConfigurator().doConfigure("log4j.properties", LogManager.getLoggerRepository());
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolRunnable.class);

    private boolean isStopped;
    private final BlockingQueue<Runnable> tasks;
    private Thread thisThread;

    public ThreadPoolRunnable(BlockingQueue<Runnable> tasks) {
        this.tasks = tasks;
        isStopped = false;
        thisThread = null;
    }

    public void stop() {
        this.isStopped = true;
        thisThread.interrupt();
    }

    @Override
    public void run() {
        thisThread = Thread.currentThread();
        while (!isStopped) {
            try {
                tasks.take().run();
            } catch (InterruptedException e) {
                if (!isStopped) {
                    LOGGER.error("Thread " + Thread.currentThread().getId() + " interrupted.", e);
                }
            }
        }
    }
}
