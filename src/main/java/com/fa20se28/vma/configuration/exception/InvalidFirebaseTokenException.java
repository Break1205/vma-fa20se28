package com.fa20se28.vma.configuration.exception;

public class InvalidFirebaseTokenException extends RuntimeException {
    public InvalidFirebaseTokenException(String e) {
        super(e);
    }

    public InvalidFirebaseTokenException(String e, Throwable cause) {
        super(e, cause);
    }
}
