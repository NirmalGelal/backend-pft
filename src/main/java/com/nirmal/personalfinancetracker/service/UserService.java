package com.nirmal.personalfinancetracker.service;


import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    public User registerUser(User user);
    public List<User> viewUsers();
    public String deleteUser(int id);
    public User updateUser(User user);

}
