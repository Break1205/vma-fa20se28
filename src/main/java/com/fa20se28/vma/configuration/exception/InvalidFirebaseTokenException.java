package com.fa20se28.vma.configuration.exception;

import com.google.firebase.auth.AuthErrorCode;

public class InvalidFirebaseTokenException extends RuntimeException {
    public AuthErrorCode authErrorCode;

    public InvalidFirebaseTokenException(String e, Throwable cause, AuthErrorCode authErrorCode) {
        super(e, cause);
        this.authErrorCode = authErrorCode;
    }
}
