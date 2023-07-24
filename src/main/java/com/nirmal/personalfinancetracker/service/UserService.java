package com.nirmal.personalfinancetracker.service;


import com.nirmal.personalfinancetracker.dto.request.AuthenticationRequest;
import com.nirmal.personalfinancetracker.dto.response.UserResponseDto;
import com.nirmal.personalfinancetracker.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    public UserDetails authenticateUser(AuthenticationRequest data);
    public UserResponseDto registerUser(User user);
    public List<UserResponseDto> viewUsers();
    public UserResponseDto viewUserById(int id);
    public UserResponseDto viewUserByEmail(String email);
    public String deleteUser(int id);
    public UserResponseDto updateUser(int userId, User user);

}
