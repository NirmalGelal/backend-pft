package com.nirmal.personalfinancetracker.service;


import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.dto.response.UserDto;
import com.nirmal.personalfinancetracker.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public UserDto registerUser(User user);
    public List<UserDto> viewUsers();
    public UserDto viewUserById(int id);
    public String deleteUser(int id);
    public UserDto updateUser(int userId, User user);

}
