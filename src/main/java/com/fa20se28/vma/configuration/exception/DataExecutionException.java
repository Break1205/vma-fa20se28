package com.fa20se28.vma.configuration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataExecutionException extends RuntimeException {
    public DataExecutionException(String e) {
        super(e);
    }

    public DataExecutionException(String e, Throwable cause) {
        super(e, cause);
    }
}
