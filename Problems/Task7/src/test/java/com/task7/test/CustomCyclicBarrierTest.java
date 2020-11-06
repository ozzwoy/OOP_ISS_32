package com.task7.test;

import com.task7.main.CustomCyclicBarrier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class CustomCyclicBarrierTest {

    @Test
    public void testConstructor() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CustomCyclicBarrier(0, () -> {}),
                        "Number of parties should be greater than 1.");
    }

    @Test
    public void testOnThreeThreads() throws ExecutionException, InterruptedException {
        final int times = 2;
        final long delta = 150L;
        final long callbackDuration = 1000L;
        final long longestTimeSleeping = 2000L;

        CustomCyclicBarrier barrier = new CustomCyclicBarrier(3, () -> {
            try {
                Thread.sleep(callbackDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < times; i++) {
            Assertions.assertTrue(barrier.getParties() == 3 && barrier.getNumberWaiting() == 0);

            Future<Long> firstTimeFuture = executorService.submit(new SampleCallable(barrier, longestTimeSleeping));
            Future<Long> secondTimeFuture = executorService.submit(new SampleCallable(barrier, 50L));
            Future<Long> thirdTimeFuture = executorService.submit(new SampleCallable(barrier, 10L));

            Thread.sleep(1000);
            Assertions.assertEquals(2, barrier.getNumberWaiting());

            long secondTime = secondTimeFuture.get();
            long thirdTime = thirdTimeFuture.get();
            long firstTime = firstTimeFuture.get();

            Assertions.assertTrue(firstTime < (callbackDuration + delta) &&
                    secondTime > (longestTimeSleeping + callbackDuration - delta) &&
                    thirdTime > (longestTimeSleeping + callbackDuration - delta));
            Assertions.assertTrue(barrier.isReleased() && !barrier.isBroken() &&
                                  barrier.getNumberWaiting() == 0);
        }
    }

    @Test
    public void testReset() throws InterruptedException {
        CustomCyclicBarrier barrier = new CustomCyclicBarrier(3, () -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Future<Long> firstTimeFuture = executorService.submit(new SampleCallable(barrier, 10L));
        Future<Long> secondTimeFuture = executorService.submit(new SampleCallable(barrier, 10L));

        Thread.sleep(50L);
        Assertions.assertEquals(2, barrier.getNumberWaiting());

        barrier.reset();
        Assertions.assertThrows(ExecutionException.class, firstTimeFuture::get, "The barrier was broken.");
        Assertions.assertThrows(ExecutionException.class, secondTimeFuture::get, "The barrier was broken.");
        Assertions.assertTrue(barrier.isBroken() && barrier.isReleased() && barrier.getNumberWaiting() == 0);
    }

    @Test
    public void testInterruption() throws InterruptedException {
        CustomCyclicBarrier barrier = new CustomCyclicBarrier(3, () -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.submit(new SampleCallable(barrier, 10L));
        executorService.submit(new SampleCallable(barrier, 10L));

        Thread.sleep(50L);
        Assertions.assertEquals(2, barrier.getNumberWaiting());

        executorService.shutdownNow();
        Thread.sleep(100L);
        Assertions.assertTrue(barrier.isBroken() && barrier.isReleased() && barrier.getNumberWaiting() == 0);
    }
}
