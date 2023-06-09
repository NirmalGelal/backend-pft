package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.model.Role;
import com.nirmal.personalfinancetracker.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RoleController {
    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @PostMapping("/role")
    public ResponseEntity<Response<Role>> addRole(@RequestBody Role role){
        Response<Role> response = new Response<>();
        Role role1 = roleServiceImpl.addRole(role);
        response.setMessage("role added");
        response.setStatus(true);
        response.setData(role1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
