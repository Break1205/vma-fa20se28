package com.fa20se28.vma.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void updateUserStatusByUserId(Long userStatusId, String userid);
}
