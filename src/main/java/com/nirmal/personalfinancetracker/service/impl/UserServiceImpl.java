package com.nirmal.personalfinancetracker.service.impl;

import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.repository.UserRepository;
import com.nirmal.personalfinancetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User registerUser(User user){
        userRepository.save(user);
        return user;
    }
    @Override
    public List<User> viewUsers(){
        return userRepository.findAll();
    }

    @Override
    public Optional<User> viewUserById(int id) {
        return userRepository.findById(id);
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
    public User updateUser(int userId, User user){
        User user1 = userRepository.findById(userId).get();
        if (user1 == null){
            return null;
        }
        user1.setName(user.getName());
        user1.setAddress(user.getAddress());
        user1.setPassword(user.getPassword());
        user1.setEmail(user.getEmail());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setRoleId(user.getRoleId());
        userRepository.save(user1);
        return user1;
    }


}
