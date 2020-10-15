package com.fa20se28.vma.util;

import com.fa20se28.vma.configuration.exception.InvalidFirebaseTokenException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Component;

@Component
public class FirebaseTokenUtil {
    public String getUserIdFromToken(String idToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            return decodedToken.getUid();
        } catch (FirebaseAuthException e) {
            throw new InvalidFirebaseTokenException("Invalid Firebase Token");
        }
    }
}
