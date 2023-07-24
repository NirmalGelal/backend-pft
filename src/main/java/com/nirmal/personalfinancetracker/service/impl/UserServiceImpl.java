package com.nirmal.personalfinancetracker.service.impl;

import com.nirmal.personalfinancetracker.dto.request.AuthenticationRequest;
import com.nirmal.personalfinancetracker.dto.response.UserResponseDto;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.repository.UserRepository;
import com.nirmal.personalfinancetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DtoMapperImpl dtoMapper;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public UserDetails authenticateUser(AuthenticationRequest data) {
        UserDetails user = userDetailsService.loadUserByUsername(data.getEmail());
        if(checkPassword(data,user)){
            return user;
        }
        return null;
    }

    private boolean checkPassword(AuthenticationRequest data, UserDetails user) {
        return (user != null && passwordEncoder.matches(data.getPassword(), user.getPassword()));
    }

    @Override
    public UserResponseDto registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(userRepository.findByEmail(user.getEmail()).isEmpty()){
            userRepository.save(user);
            return dtoMapper.toUserDto(user);
        }
        return null;
    }
    @Override
    public List<UserResponseDto> viewUsers(){
        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for(User user:users) {
            userResponseDtos.add(dtoMapper.toUserDto(user));
        }
        return userResponseDtos;
    }

    @Override
    public UserResponseDto viewUserById(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(user -> dtoMapper.toUserDto(user)).orElse(null);
    }

    @Override
    public UserResponseDto viewUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        User principalUser = getCurrentUser();
        if(userOptional.isPresent() && userOptional.get().getId() == principalUser.getId()){
            return dtoMapper.toUserDto(userOptional.get());
        }
        return null;
    }

    @Override
    public String deleteUser(int id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.deleteById(id);
            return "success";
        }
        return "user with id: "+id+ " not present.";
    }
    @Override
    public UserResponseDto updateUser(int userId, User user){
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()){
            return null;
        }
        User user1 = userOptional.get();
        user1.setName(user.getName());
        user1.setAddress(user.getAddress());
        user1.setPassword(user.getPassword());
        user1.setEmail(user.getEmail());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setRoleId(user.getRoleId());
        userRepository.save(user1);
        return dtoMapper.toUserDto(user1);
    }

    public User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
