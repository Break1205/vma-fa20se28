package com.fa20se28.vma.configuration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TemplateException extends RuntimeException {
    public TemplateException(String e) {
        super(e);
    }

    public TemplateException(String e, Throwable cause)
    {
        super(e, cause);
    }
}
