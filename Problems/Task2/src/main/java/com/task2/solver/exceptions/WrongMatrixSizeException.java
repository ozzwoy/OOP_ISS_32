package com.task2.solver.exceptions;

public class WrongMatrixSizeException extends Exception {
    public WrongMatrixSizeException() {
        super();
    }

    public WrongMatrixSizeException(String message) {
        super(message);
    }
}
