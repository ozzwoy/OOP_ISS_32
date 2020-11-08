package com.task6.test;

import com.task6.main.NonblockingMSQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class NonblockingMSQueueTest {

    @Test
    public void testOnSingleThread() {
        NonblockingMSQueue<Integer> queue = new NonblockingMSQueue<>();
        final int size = 5;
        int[] expected = IntStream.range(0, size).toArray();
        int[] result = new int[size];
        int times = 2;

        for (int k = 0; k < times; k++) {
            for (int i = 0; i < size; i++) {
                queue.enqueue(i);
            }
            for (int i = 0; i < size; i++) {
                result[i] = queue.dequeue();
            }

            Assertions.assertTrue(Arrays.equals(expected, result));
            Assertions.assertNull(queue.dequeue());
        }
    }

    @Test
    public void testOnIntegerArrays() {
        NonblockingMSQueue<Integer> queue = new NonblockingMSQueue<>();

        final int chunkSize = 1000;
        final int numOfThreads = 2;

        List<QueueTestCallable> callables = new ArrayList<>(numOfThreads);
        List<Future<List<Integer>>> futures = new ArrayList<>(numOfThreads);
        List<Integer> result = new ArrayList<>(chunkSize * numOfThreads);

        for (int i = 0; i < numOfThreads; i++) {
            callables.add(new QueueTestCallable(queue, IntStream.range(i * chunkSize, (i + 1) * chunkSize).toArray()));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads);
        for (int i = 0; i < numOfThreads; i++) {
            futures.add(executorService.submit(callables.get(i)));
        }

        for (Future<List<Integer>> future : futures) {
            try {
                result.addAll(future.get());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<Integer> expected = new ArrayList<>(chunkSize * numOfThreads);
        for (int i = 0; i < chunkSize * numOfThreads; i++) {
            expected.add(i);
        }

        Assertions.assertEquals(expected, result);
    }
}
