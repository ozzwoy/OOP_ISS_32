package com.task6.main;

import java.util.concurrent.atomic.AtomicStampedReference;

public class NonblockingMSQueue<T> {
    private AtomicStampedReference<Node<T>> head;
    private AtomicStampedReference<Node<T>> tail;

    public NonblockingMSQueue() {
        Node<T> dummyNode = new Node<>(null, new AtomicStampedReference<>(null, 0));
        this.head = new AtomicStampedReference<>(dummyNode, 0);
        this.tail = new AtomicStampedReference<>(dummyNode, 0);
    }

    public void enqueue(T t) {
        Node<T> newNode = new Node<>(t, new AtomicStampedReference<>(null, 0));
        AtomicStampedReference<Node<T>> prevTail;
        AtomicStampedReference<Node<T>> prevNext;

        while (true) {
            prevTail = tail;
            prevNext = tail.getReference().getNext();

            if (tail.equals(prevTail)) {
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

            if (head.equals(prevHead)) {
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
