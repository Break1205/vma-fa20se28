package com.fa20se28.vma.configuration.exception;

public class InvalidParamException extends RuntimeException {
    public InvalidParamException(String e) {
        super(e);
    }

    public InvalidParamException(String e, Throwable cause) {
        super(e, cause);
    }
}