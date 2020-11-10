package org.javaschool.exception;

public class TrainsNotFoundException extends RuntimeException {

    public TrainsNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}