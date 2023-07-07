package com.nirmal.personalfinancetracker.service.impl;

import com.nirmal.personalfinancetracker.dto.response.UserDto;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.repository.UserRepository;
import com.nirmal.personalfinancetracker.service.DtoMapper;
import com.nirmal.personalfinancetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Override
    public UserDto registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return dtoMapper.toUserDto(user);
    }
    @Override
    public List<UserDto> viewUsers(){
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for(User user:users) {
            userDtos.add(dtoMapper.toUserDto(user));
        }
        return userDtos;
    }

    @Override
    public UserDto viewUserById(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(user -> dtoMapper.toUserDto(user)).orElse(null);
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
    public UserDto updateUser(int userId, User user){
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


}
