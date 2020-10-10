package com.task3;

import com.task3.utils.Pair;

import java.util.Deque;
import java.util.LinkedList;

public class ThreadGroupStatusDaemon extends Thread {
    private ThreadGroup threadGroup;
    private long period;
    private static final int INDENT = 10;

    ThreadGroupStatusDaemon(ThreadGroup threadGroup, long periodInMillis) {
        if (periodInMillis < 1) {
            throw new IllegalArgumentException("A period should be greater than 0.");
        }
        this.period = periodInMillis;
        this.threadGroup = threadGroup;
        this.setDaemon(true);
    }

    private void printIndents(int level) {
        int currentLevel = 0;
        String gapString = " ".repeat(INDENT);

        while (currentLevel < level) {
            System.out.print("|" + gapString);
            currentLevel++;
        }
        System.out.print("|-");
    }

    private void printThreadGroup(ThreadGroup group, int level) {
        printIndents(level);
        System.out.println("Thread Group: " + group.toString());

        Thread[] threadList = new Thread[group.activeCount()];
        group.enumerate(threadList, false);

        for (Thread current : threadList) {
            if (current == null) break;
            printIndents(level);
            System.out.println("Thread: " + current.toString());
        }
    }

    private void printStatus() {
        int level = 0;
        Deque<Pair<ThreadGroup, Integer>> stack = new LinkedList<>();
        Pair<ThreadGroup, Integer> item;

        stack.push(new Pair<>(threadGroup, level));

        while (!stack.isEmpty()) {
            item = stack.peek();
            stack.pop();
            printThreadGroup(item.getFirst(), item.getSecond());

            ThreadGroup[] groupList = new ThreadGroup[item.getFirst().activeGroupCount()];
            item.getFirst().enumerate(groupList, false);
            for (ThreadGroup current : groupList) {
                stack.push(new Pair<>(current, item.getSecond() + 1));
            }
        }
    }

    @Override
    public void run() {
        long previousTimePoint = System.currentTimeMillis();
        long currentTimePoint;

        while (true) {
            currentTimePoint = System.currentTimeMillis();
            if (currentTimePoint - previousTimePoint >= period) {
                System.out.print("THREAD GROUP HIERARCHY:\n");
                printStatus();
                System.out.print("\n\n");
                previousTimePoint = currentTimePoint;
            }
        }
    }
}
