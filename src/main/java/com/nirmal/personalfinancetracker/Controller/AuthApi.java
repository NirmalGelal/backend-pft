package com.nirmal.personalfinancetracker.Controller;


import com.nirmal.personalfinancetracker.config.jwt.JwtService;
import com.nirmal.personalfinancetracker.dto.request.AuthenticationRequest;
import com.nirmal.personalfinancetracker.dto.request.UserRequestDto;
import com.nirmal.personalfinancetracker.dto.response.AuthenticationResponse;
import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.dto.response.UserResponseDto;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.service.UserService;
import com.nirmal.personalfinancetracker.service.impl.DtoMapperImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthApi {

    private final UserService userService;
    private final JwtService jwtService;
    private final DtoMapperImpl dtoMapper;

    @PostMapping("/signin")
    public Response<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest data, HttpServletRequest request) throws Exception {
        Response<AuthenticationResponse> response = new Response<>();
        UserDetails user = userService.authenticateUser(data);
        if (user == null) {
            throw new Exception("User not found");
        }
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .username(data.getEmail())
                .build();
        response.successResponse(authenticationResponse,"tokens generated successfully");
        return response;
    }

    @PostMapping("/user")
    public Response<UserResponseDto> addUser(@RequestBody UserRequestDto userRequestDto) {
        Response<UserResponseDto> response = new Response<>();
        User user = dtoMapper.toUserEntity(userRequestDto);
        UserResponseDto userResponseDto = userService.registerUser(user);
        response.successResponse(userResponseDto,"user registered successfully");
        return response;
    }


    @PostMapping("/refresh-token")
    public Response<AuthenticationResponse> refreshToken(
            HttpServletRequest req,
            HttpServletResponse res
    ) throws IOException {
        Response<AuthenticationResponse> response = new Response<>();
        AuthenticationResponse authenticationResponse = jwtService.refreshToken(req, res);
        response.successResponse(authenticationResponse,"access token revived successfully");
        return response;
    }
}
