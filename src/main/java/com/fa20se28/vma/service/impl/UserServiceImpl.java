package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.UserComponent;
import com.fa20se28.vma.request.DriverReq;
import com.fa20se28.vma.service.FirebaseService;
import com.fa20se28.vma.service.UserService;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserComponent userComponent;
    private final FirebaseService firebaseService;

    public UserServiceImpl(UserComponent userComponent, FirebaseService firebaseService) {
        this.userComponent = userComponent;
        this.firebaseService = firebaseService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    @Transactional
    public int createDriver(DriverReq driverReq) throws FirebaseAuthException {
        if (driverReq.getUserStatusId() == 2) {
            firebaseService.createUserRecord(driverReq);
        }
        return userComponent.createDriver(driverReq);
    }
}
