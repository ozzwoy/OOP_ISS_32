package com.task6.test;

import com.task6.main.NonblockingMSQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class QueueTestCallable implements Callable<List<Integer>> {
    NonblockingMSQueue<Integer> queue;
    int[] testValues;

    public QueueTestCallable(NonblockingMSQueue<Integer> queue, int[] testValues) {
        this.queue = queue;
        this.testValues = testValues;
    }

    @Override
    public List<Integer> call() {
        List<Integer> result = new ArrayList<>(testValues.length);
        final int chunkSize = 5;
        int num_of_chunks = testValues.length / chunkSize;

        for (int i = 0; i < num_of_chunks; i++) {
            for (int j = 0; j < chunkSize; j++) {
                queue.enqueue(testValues[i * chunkSize + j]);
            }
            for (int j = 0; j < chunkSize; j++) {
                result.add(queue.dequeue());
            }
        }
        for (int i = chunkSize * num_of_chunks; i < result.size(); i++) {
            queue.enqueue(testValues[i]);
        }
        for (int i = chunkSize * num_of_chunks; i < result.size(); i++) {
            result.add(queue.dequeue());
        }

        return result;
    }
}
