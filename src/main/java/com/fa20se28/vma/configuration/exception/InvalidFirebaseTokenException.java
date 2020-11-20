package com.fa20se28.vma.configuration.exception;

import com.google.firebase.auth.AuthErrorCode;
import com.google.firebase.auth.FirebaseAuthException;

public class InvalidFirebaseTokenException extends RuntimeException {
    public AuthErrorCode authErrorCode;

    public InvalidFirebaseTokenException(String e, FirebaseAuthException cause, AuthErrorCode authErrorCode) {
        super(e, cause);
        this.authErrorCode = authErrorCode;
    }
}
