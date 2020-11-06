package com.task7.main;

import java.util.concurrent.BrokenBarrierException;
//import java.util.concurrent.TimeUnit;

public class CustomCyclicBarrier {
    private final int parties;
    private int numberWaiting;
    private final Runnable barrierAction;
    private boolean broken;
    private boolean released;

    public CustomCyclicBarrier(int parties) throws IllegalArgumentException {
        if (parties < 1) {
            throw new IllegalArgumentException("Number of parties should be greater than 1.");
        }
        this.parties = parties;
        this.barrierAction = null;
        this.numberWaiting = parties;
        this.broken = false;
        this.released = false;
    }

    public CustomCyclicBarrier(int parties, Runnable barrierAction) throws IllegalArgumentException {
        if (parties < 1) {
            throw new IllegalArgumentException("Number of parties should be greater than 1.");
        }
        this.parties = parties;
        this.barrierAction = barrierAction;
        this.numberWaiting = parties;
        this.broken = false;
        this.released = false;
    }

    public int getParties() {
        return parties;
    }

    public int getNumberWaiting() {
        return numberWaiting;
    }

    public boolean isBroken() {
        return broken;
    }

    public boolean isReleased() {
        return released;
    }

    public synchronized void reset() {
        numberWaiting = parties;
        broken = true;
        released = true;
        notifyAll();
    }

    public synchronized int await() throws BrokenBarrierException, InterruptedException {
        broken = false;
        numberWaiting--;
        int arrivalIndex = numberWaiting;

        while (numberWaiting > 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                reset();
                throw e;
            }

            if (released) {
                if (broken) {
                    throw new BrokenBarrierException("The barrier was broken.");
                }
                break;
            }
        }

        if (arrivalIndex == 0) {
            numberWaiting = parties;
            released = true;
            notifyAll();
            if (barrierAction != null) {
                barrierAction.run();
            }
        }

        return arrivalIndex;
    }

    /*public synchronized int await(long timeout, TimeUnit unit) throws BrokenBarrierException, InterruptedException {
        broken = false;
        numberWaiting--;
        int arrivalIndex = numberWaiting;

        while (numberWaiting > 0) {
            try {
                this.wait(unit.toMillis(timeout));
            } catch (InterruptedException e) {
                reset();
                throw e;
            }

            if (released) {
                if (broken) {
                    throw new BrokenBarrierException("The barrier was broken.");
                }
                break;
            } else {
                reset();
                throw new BrokenBarrierException("The barrier was broken. Specified timeout elapsed.");
            }
        }

        if (arrivalIndex == 0) {
            numberWaiting = parties;
            released = true;
            notifyAll();
            if (barrierAction != null) {
                barrierAction.run();
            }
        }

        return arrivalIndex;
    }*/
}
