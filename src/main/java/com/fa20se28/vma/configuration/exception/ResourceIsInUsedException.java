package com.fa20se28.vma.configuration.exception;

public class ResourceIsInUsedException extends RuntimeException {
    public ResourceIsInUsedException(String e) {
        super(e);
    }

    public ResourceIsInUsedException(String e, Throwable cause) {
        super(e, cause);
    }
}