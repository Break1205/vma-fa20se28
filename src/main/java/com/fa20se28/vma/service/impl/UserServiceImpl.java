package com.fa20se28.vma.service.impl;

import com.fa20se28.vma.component.impl.UserComponentImpl;
import com.fa20se28.vma.response.UserDTO;
import com.fa20se28.vma.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserComponentImpl userComponent;

    public UserServiceImpl(UserComponentImpl userComponent) {
        this.userComponent = userComponent;
    }

    @Override
    public List<UserDTO> getDrivers() {

        return null;
    }



    @Override
    public UserDTO findDriverById(Long userId) {
        return null;
    }
}
