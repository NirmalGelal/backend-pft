package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.request.UserRequestDto;
import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.dto.response.UserResponseDto;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.service.impl.DtoMapperImpl;
import com.nirmal.personalfinancetracker.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private DtoMapperImpl dtoMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response<List<UserResponseDto>>> viewUserList() {
        Response<List<UserResponseDto>> response = new Response<>();
        List<UserResponseDto> userResponseDtos = userServiceImpl.viewUsers();
        if (!userResponseDtos.isEmpty()) {
            response.successResponse(userResponseDtos, "list retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setStatus(false);
        response.setMessage("database is empty");
        response.setData(userResponseDtos);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Response<UserResponseDto>> editUser(@PathVariable int id, @RequestBody UserRequestDto userRequestDto, Authentication authentication) {
        Response<UserResponseDto> response = new Response<>();
        if(id == ((User)authentication.getPrincipal()).getId()) {
            User user = dtoMapper.toUserEntity(userRequestDto);
            user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
            UserResponseDto user1 = userServiceImpl.updateUser(id, user);
            if (user1 != null) {
                response.successResponse(user1, "user updated successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        response.failureResponse("User not Authorized");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Response<String>> deleteUser(@PathVariable int id, Authentication authentication) {
        Response<String> response = new Response<>();
        if(id == ((User)authentication.getPrincipal()).getId()) {
            String data = userServiceImpl.deleteUser(id);
            if (data.equals("success")) {
                response.successResponse(data, "user deleted successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        response.failureResponse("User not Authorized");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
