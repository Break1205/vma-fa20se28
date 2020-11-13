package com.fa20se28.vma.configuration.exception;

public class RequestAlreadyHandledException extends RuntimeException {
    public RequestAlreadyHandledException(String e) {
        super(e);
    }

    public RequestAlreadyHandledException(String e, Throwable cause) {
        super(e, cause);
    }
}