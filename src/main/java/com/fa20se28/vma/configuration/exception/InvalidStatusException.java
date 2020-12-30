package com.fa20se28.vma.configuration.exception;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String e) {
        super(e);
    }

    public InvalidStatusException(String e, Throwable throwable) {
        super(e, throwable);
    }
}
