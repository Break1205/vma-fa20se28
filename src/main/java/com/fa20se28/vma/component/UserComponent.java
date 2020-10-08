package com.fa20se28.vma.component;

import com.fa20se28.vma.model.User;

import java.util.List;

public interface UserComponent {
    public User findDriverById (Long userId);

    public List<User> findDrivers();
}
