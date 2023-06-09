package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/user")
    public ResponseEntity<Response<User>> registerUser(@RequestBody User user){
        Response<User> response = new Response<>();
        User user1 = userServiceImpl.registerUser(user);
        response.setStatus(true);
        response.setData(user1);
        response.setMessage("user registered successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
