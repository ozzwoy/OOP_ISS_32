package com.task6.main;

import java.util.concurrent.atomic.AtomicStampedReference;

public class NonblockingMSQueue<T> {
    private final AtomicStampedReference<Node<T>> head;
    private final AtomicStampedReference<Node<T>> tail;

    public NonblockingMSQueue() {
        Node<T> dummyNode = new Node<>(null, new AtomicStampedReference<>(null, 0));
        this.head = new AtomicStampedReference<>(dummyNode, 0);
        this.tail = new AtomicStampedReference<>(dummyNode, 0);
    }

    public void enqueue(T t) {
        Node<T> newNode = new Node<>(t, new AtomicStampedReference<>(null, 0));
        AtomicStampedReference<Node<T>> prevTail = new AtomicStampedReference<>(null, 0);
        AtomicStampedReference<Node<T>> prevNext = new AtomicStampedReference<>(null, 0);

        while (true) {
            prevTail.set(tail.getReference(), tail.getStamp());
            prevNext.set(tail.getReference().getNext().getReference(), tail.getReference().getNext().getStamp());

            if (prevTail.getReference() == tail.getReference() && prevTail.getStamp() == tail.getStamp()) {
                if (prevNext.getReference() == null) {
                    if (prevTail.getReference().getNext().compareAndSet(prevNext.getReference(),
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
        AtomicStampedReference<Node<T>> prevHead = new AtomicStampedReference<>(null, 0);
        AtomicStampedReference<Node<T>> prevTail = new AtomicStampedReference<>(null, 0);
        AtomicStampedReference<Node<T>> prevNext = new AtomicStampedReference<>(null, 0);
        T dequeuedValue;

        while (true) {
            prevHead.set(head.getReference(), head.getStamp());
            prevTail.set(tail.getReference(), tail.getStamp());
            prevNext.set(head.getReference().getNext().getReference(), head.getReference().getNext().getStamp());

            if (prevHead.getReference() == head.getReference() && prevHead.getStamp() == head.getStamp()) {
                if (prevHead.getReference() == prevTail.getReference()) {
                    if (prevNext.getReference() == null) {
                        return null;
                    }
                    tail.compareAndSet(prevTail.getReference(), prevNext.getReference(), prevTail.getStamp(),
                                       prevTail.getStamp() + 1);
                } else {
                    dequeuedValue = prevNext.getReference().getValue();
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
