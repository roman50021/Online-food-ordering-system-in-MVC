package com.example.onlinelearningplatform.service.services;

import com.example.onlinelearningplatform.dto.user.UserDto;
import com.example.onlinelearningplatform.models.User;

import java.util.List;

public interface UserService {
    void save(UserDto userDto);
    User findByEmail(String email);
    List<User> getAllUsers();

}

