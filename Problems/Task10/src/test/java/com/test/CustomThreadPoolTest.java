package com.test;

import com.main.CustomThreadPool;
import com.main.InvalidPoolParameterException;
import com.main.TaskSubmittingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomThreadPoolTest {
    @Test
    public void testOnZeroThreads() {
        Assertions.assertThrows(InvalidPoolParameterException.class,
                                () -> new CustomThreadPool(0, 5),
                                "Number of threads should be a positive integer. Provided: 0.");
    }

    @Test
    public void testOnZeroMaxTasks() {
        Assertions.assertThrows(InvalidPoolParameterException.class,
                                () -> new CustomThreadPool(3, 0),
                                "Maximal number of tasks should be a positive integer. Provided: 0.");
    }

    @Test
    public void testSingleThreadPool() throws InvalidPoolParameterException, TaskSubmittingException {
        CustomThreadPool pool = new CustomThreadPool(1, 5);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            pool.submit(() -> threads.add(Thread.currentThread()));
        }
        pool.shutdown();
        Assertions.assertEquals(threads.get(0), threads.get(1));
        Assertions.assertTrue(pool.isShutdown());
    }

    @Test
    public void testPoolWithTwoThreads() throws InvalidPoolParameterException, TaskSubmittingException {
        CustomThreadPool pool = new CustomThreadPool(2, 10);
        Set<Thread> threads = new HashSet<>();
        AtomicInteger tasksDone = new AtomicInteger(0);
        for (int i = 0; i < 6; i++) {
            pool.submit(() -> {
                threads.add(Thread.currentThread());
                tasksDone.incrementAndGet();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        pool.shutdown();
        Assertions.assertEquals(2, threads.size());
        Assertions.assertEquals(6, tasksDone.get());
        Assertions.assertTrue(pool.isShutdown());
    }

    @Test
    public void testShutdownNow() throws InterruptedException, InvalidPoolParameterException, TaskSubmittingException {
        CustomThreadPool pool = new CustomThreadPool(2, 10);
        AtomicInteger tasksDone = new AtomicInteger(0);
        for (int i = 0; i < 4; i++) {
            pool.submit(() -> {
                tasksDone.incrementAndGet();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.sleep(50);
        pool.shutdownNow();
        Assertions.assertEquals(2, tasksDone.get());
        Assertions.assertTrue(pool.isShutdown());
    }

    @Test
    public void testOnTooManyTasks() throws InvalidPoolParameterException {
        CustomThreadPool pool = new CustomThreadPool(3, 1);
        Assertions.assertThrows(TaskSubmittingException.class, () -> {
                    for (int i = 0; i < 10; i++) {
                        pool.submit(() -> {});
                    }
                },
                "The thread pool is full.");
    }

    @Test
    public void testUsingAfterShutdown() throws InterruptedException, InvalidPoolParameterException {
        CustomThreadPool pool = new CustomThreadPool(3, 3);
        Thread.sleep(10);
        pool.shutdown();
        Assertions.assertThrows(TaskSubmittingException.class, () -> pool.submit(() -> {}),
                                "The thread pool has been shutdown.");
    }
}
