package com.task6.main;

import java.util.concurrent.atomic.AtomicStampedReference;

class Node<T> {
    private T value;
    private AtomicStampedReference<Node<T>> next;

    public Node(T value, AtomicStampedReference<Node<T>> next) {
        this.value = value;
        this.next = next;
    }

    public T getValue() {
        return value;
    }

    public AtomicStampedReference<Node<T>> getNext() {
        return next;
    }
}
