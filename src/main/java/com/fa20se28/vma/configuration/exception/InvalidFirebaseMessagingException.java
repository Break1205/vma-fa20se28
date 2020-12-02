package com.fa20se28.vma.configuration.exception;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MessagingErrorCode;

public class InvalidFirebaseMessagingException extends RuntimeException {
    public MessagingErrorCode messagingErrorCode;

    public InvalidFirebaseMessagingException(String e, FirebaseMessagingException cause, MessagingErrorCode messagingErrorCode) {
        super(e, cause);
        this.messagingErrorCode = messagingErrorCode;
    }
}
