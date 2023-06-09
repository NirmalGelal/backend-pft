package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/users")
    public ResponseEntity<Response<List<User>>> viewUserList(){
        Response<List<User>> response = new Response<>();
        List<User> users = userServiceImpl.viewUsers();
        if(!users.isEmpty()){
            response.setData(users);
            response.setStatus(true);
            response.setMessage("list retrieved successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.setStatus(false);
        response.setMessage("database is empty");
        response.setData(users);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Response<User>> viewUserById(@PathVariable int id){
        Response<User> response = new Response<>();
        Optional<User> user = userServiceImpl.viewUserById(id);
        if(!user.isEmpty()){
            response.setData(user.get());
            response.setStatus(true);
            response.setMessage("user retrieved successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.setStatus(false);
        response.setMessage("invalid Id");
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Response<User>> editUser(@PathVariable int id,@RequestBody User user){
        Response<User> response = new Response<>();
        User user1 = userServiceImpl.updateUser(id,user);
        if(user1!=null){
            response.setData(user1);
            response.setMessage("user updated successfully");
            response.setStatus(true);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setMessage("Id invalid");
        response.setStatus(false);
        response.setData(null);
        return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Response<String>> deleteUser(@PathVariable int id){
        Response<String> response = new Response<>();
        String data = userServiceImpl.deleteUser(id);
        if(data=="success"){
            response.setData(data);
            response.setStatus(true);
            response.setMessage("user deleted successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.setData(null);
        response.setStatus(false);
        response.setMessage(data);
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
