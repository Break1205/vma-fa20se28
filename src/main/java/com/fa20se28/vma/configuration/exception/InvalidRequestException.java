package com.fa20se28.vma.configuration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String e) {
        super(e);
    }

    public InvalidRequestException(String e, Throwable cause) {
        super(e, cause);
    }
}
