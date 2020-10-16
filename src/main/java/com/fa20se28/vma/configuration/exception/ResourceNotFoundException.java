package com.fa20se28.vma.configuration.exception;

import java.util.function.Supplier;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String e) {
        super(e);
    }

    public ResourceNotFoundException(String e, Throwable cause) {
        super(e, cause);
    }
}
