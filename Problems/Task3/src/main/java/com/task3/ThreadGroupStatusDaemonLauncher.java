package com.task3;

public class ThreadGroupStatusDaemonLauncher {
    public static Thread launch(ThreadGroup threadGroup, long periodInMillis) {
        ThreadGroupStatusDaemon daemon = new ThreadGroupStatusDaemon(threadGroup, periodInMillis);
        Thread daemonThread = new Thread(daemon);
        daemonThread.setDaemon(true);
        daemonThread.start();
        return daemonThread;
    }
}
