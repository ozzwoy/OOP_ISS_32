package com.task3;

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

        if (level == 0) return;
        else {
            System.out.print(gapString);
            currentLevel++;
        }

        while (currentLevel < level) {
            System.out.print("|" + gapString);
            currentLevel++;
        }
        System.out.print("|-");
    }

    void printThread(Thread current, int level) {
        printIndents(level);
        System.out.println("Thread: " + current.getName() + ", priority: " + current.getPriority() + ", group: " +
                           current.getThreadGroup().getName() + ", state: " + current.getState());
    }

    void printThreadGroup(ThreadGroup current, int level) {
        printIndents(level);
        System.out.println("Thread Group: " + current.getName() + ", max priority: " + current.getMaxPriority() +
                           ", parent: " + current.getParent().getName());
    }

    void printThreadGroupContents(ThreadGroup current, int level) {
        Thread[] threadList = new Thread[current.activeCount()];
        current.enumerate(threadList, false);

        for (Thread thread : threadList) {
            if (thread == null) break;
            printThread(thread, level);
        }

        ThreadGroup[] groupList = new ThreadGroup[current.activeGroupCount()];
        current.enumerate(groupList, false);
        for (ThreadGroup group : groupList) {
            if (group == null) break;
            printThreadGroup(group, level);
            printThreadGroupContents(group, level + 1);
        }
    }

    private void printStatus() {
        printThreadGroup(threadGroup, 0);
        printThreadGroupContents(threadGroup, 1);
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
