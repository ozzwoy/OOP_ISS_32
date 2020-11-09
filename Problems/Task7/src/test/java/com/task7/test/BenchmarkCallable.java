package com.task7.test;

import com.task7.main.CustomCyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;

public class BenchmarkCallable implements Callable<Long> {
    private final CustomCyclicBarrier barrier;
    private final long timeSleeping;

    public BenchmarkCallable(CustomCyclicBarrier barrier, long timeSleeping) {
        this.barrier = barrier;
        this.timeSleeping = timeSleeping;
    }

    @Override
    public Long call() throws BrokenBarrierException, InterruptedException {
        Thread.sleep(timeSleeping);
        long start = System.currentTimeMillis();
        barrier.await();
        return System.currentTimeMillis() - start;
    }
}
