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
    public String deleteUser(int id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.deleteById(id);
            return "user deleted successfully";
        }
        return "user with id: "+user.get().getId()+ " not present.";
    }
    @Override
    public User updateUser(User user){
        userRepository.save(user);
        return user;
    }


}
