package com.fa20se28.vma.service;

import com.fa20se28.vma.response.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserDTO> getDrivers();

    UserDTO findDriverById(Long userId);


}
