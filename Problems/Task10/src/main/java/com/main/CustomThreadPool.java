package com.main;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomThreadPool {
    private final BlockingQueue<Runnable> tasks;
    private final List<ThreadPoolRunnable> workers;
    private boolean isShutdown;

    public CustomThreadPool(int numOfThreads, int maxNumOfTasks) throws InvalidPoolParameterException {
        if (numOfThreads < 1) {
            throw new InvalidPoolParameterException("Number of threads should be a positive integer. Provided: " +
                                                    numOfThreads + ".");
        }
        if (maxNumOfTasks < 1) {
            throw new InvalidPoolParameterException("Maximal number of tasks should be a positive integer. Provided: " +
                                                     maxNumOfTasks + ".");
        }

        tasks = new ArrayBlockingQueue<>(maxNumOfTasks);
        workers = Stream.generate(() -> new ThreadPoolRunnable(tasks))
                        .limit(numOfThreads)
                        .collect(Collectors.toList());
        isShutdown = false;

        for (Runnable worker : workers) {
            new Thread(worker).start();
        }
    }

    public boolean isShutdown() {
        return isShutdown;
    }

    public synchronized void submit(Runnable task) throws TaskSubmittingException {
        if (isShutdown) {
            throw new TaskSubmittingException("The thread pool has been shutdown.");
        }
        if (!tasks.offer(task)) {
            throw new TaskSubmittingException("The thread pool is full.");
        }
    }

    public synchronized void shutdown() {
        if (!isShutdown) {
            isShutdown = true;
            while (!tasks.isEmpty()) {
                Thread.yield();
            }
            for (ThreadPoolRunnable worker : workers) {
                worker.stop();
            }
        }
    }

    public synchronized void shutdownNow() {
        if (!isShutdown) {
            isShutdown = true;
            for (ThreadPoolRunnable worker : workers) {
                worker.stop();
            }
        }
    }
}
