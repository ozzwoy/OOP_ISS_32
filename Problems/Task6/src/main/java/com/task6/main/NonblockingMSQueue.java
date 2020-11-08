package com.task6.main;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicStampedReference;

public class NonblockingMSQueue<T> {
    private AtomicStampedReference<Node<T>> head;
    private AtomicStampedReference<Node<T>> tail;
    int size;

    public NonblockingMSQueue() {
        Node<T> dummyNode = new Node<>(null, null);
        this.head = new AtomicStampedReference<>(dummyNode, 0);
        this.tail = new AtomicStampedReference<>(dummyNode, 0);
        this.size = 0;
    }

    @SafeVarargs
    public NonblockingMSQueue(T... a) {
        this();
        for (T t : a) {
            enqueue(t);
        }
    }

    public int size() {
        return 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(T t) {
        Node<T> newNode = new Node<>(t, null);
        AtomicStampedReference<Node<T>> prevTail;
        AtomicStampedReference<Node<T>> prevNext;

        while (true) {
            prevTail = tail;
            prevNext = tail.getReference().getNext();

            if (prevTail == tail) {
                if (prevNext.getReference() == null) {
                    if (tail.getReference().getNext().compareAndSet(prevNext.getReference(),
                                                                    newNode,
                                                                    prevNext.getStamp(),
                                                           prevNext.getStamp() + 1)) {
                        break;
                    }
                } else {
                    tail.compareAndSet(prevTail.getReference(), prevNext.getReference(), prevTail.getStamp(),
                              prevTail.getStamp() + 1);
                }
            }
        }

        tail.compareAndSet(prevTail.getReference(), newNode, prevTail.getStamp(), prevTail.getStamp() + 1);
    }

    public T dequeue() {
        AtomicStampedReference<Node<T>> prevHead;
        AtomicStampedReference<Node<T>> prevTail;
        AtomicStampedReference<Node<T>> prevNext;
        T dequeuedValue;

        while (true) {
            prevHead = head;
            prevTail = tail;
            prevNext = head.getReference().getNext();

            if (prevHead == head) {
                if (prevHead.getReference() == prevTail.getReference()) {
                    if (prevNext.getReference() == null) {
                        return null;
                    }
                    prevTail.compareAndSet(prevTail.getReference(), prevNext.getReference(), prevTail.getStamp(),
                                  prevTail.getStamp() + 1);
                } else {
                    dequeuedValue = prevHead.getReference().getValue();
                    if (head.compareAndSet(prevHead.getReference(), prevNext.getReference(), prevHead.getStamp(),
                                           prevHead.getStamp() + 1)) {
                        break;
                    }
                }
            }
        }

        return dequeuedValue;
    }
}
