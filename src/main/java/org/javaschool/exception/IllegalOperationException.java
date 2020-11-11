package org.javaschool.exception;

public class IllegalOperationException extends RuntimeException {

    public IllegalOperationException(String errorMessage) {
        super(errorMessage);
    }
}
