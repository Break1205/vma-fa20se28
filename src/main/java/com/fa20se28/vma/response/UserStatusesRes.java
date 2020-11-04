package com.fa20se28.vma.response;

import com.fa20se28.vma.enums.UserStatus;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserStatusesRes {
    public List<UserStatus> getUserStatus() {
        return Stream.of(UserStatus.values()).collect(Collectors.toList());
    }
}
