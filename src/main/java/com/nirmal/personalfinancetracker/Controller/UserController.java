package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.dto.response.UserResponseDto;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.service.impl.DtoMapperImpl;
import com.nirmal.personalfinancetracker.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private DtoMapperImpl dtoMapper;

    @PostMapping("/user")
    public ResponseEntity<Response<UserResponseDto>> registerUser(@RequestBody User user) {
        Response<UserResponseDto> response = new Response<>();
        UserResponseDto userResponseDto = userServiceImpl.registerUser(user);
        response.setStatus(true);
        response.setData(userResponseDto);
        response.setMessage("user registered successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<Response<List<UserResponseDto>>> viewUserList() {
        Response<List<UserResponseDto>> response = new Response<>();
        List<UserResponseDto> userResponseDtos = userServiceImpl.viewUsers();
        if (!userResponseDtos.isEmpty()) {
            response.setData(userResponseDtos);
            response.setStatus(true);
            response.setMessage("list retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setStatus(false);
        response.setMessage("database is empty");
        response.setData(userResponseDtos);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Response<UserResponseDto>> viewUserById(@PathVariable int id) {
        Response<UserResponseDto> response = new Response<>();
        UserResponseDto user = userServiceImpl.viewUserById(id);
        if (user != null) {
            response.setData(user);
            response.setStatus(true);
            response.setMessage("user retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setStatus(false);
        response.setMessage("invalid Id");
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Response<UserResponseDto>> editUser(@PathVariable int id, @RequestBody User user) {
        Response<UserResponseDto> response = new Response<>();
        UserResponseDto user1 = userServiceImpl.updateUser(id, user);
        if (user1 != null) {
            response.successResponse(user1,"user updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("Id invalid");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Response<String>> deleteUser(@PathVariable int id) {
        Response<String> response = new Response<>();
        String data = userServiceImpl.deleteUser(id);
        if (data.equals("success")) {
            response.successResponse(data,"user deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse(data);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
