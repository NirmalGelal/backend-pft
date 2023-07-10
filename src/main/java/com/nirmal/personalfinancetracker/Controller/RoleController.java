package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.model.Role;
import com.nirmal.personalfinancetracker.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {
    @Autowired
    private RoleServiceImpl roleServiceImpl;

    @PostMapping("/role")
    public ResponseEntity<Response<Role>> addRole(@RequestBody Role role){
        Response<Role> response = new Response<>();
        Role role1 = roleServiceImpl.addRole(role);
        response.successResponse(role1,"role added");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<Response<List<Role>>> viewRoleList(){
        Response<List<Role>> response = new Response<>();
        List<Role> roles = roleServiceImpl.viewRoleList();
        response.successResponse(roles,"list successfully retrieved");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/roles/{id}")
    public ResponseEntity<Response<Role>> viewRoleById(@PathVariable int id){
        Response<Role> response = new Response<>();
        Role role = roleServiceImpl.viewRoleById(id);
        response.successResponse(role,"role successfully retrieved");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("role/{id}")
    public ResponseEntity<Response<Role>> updateRole(@PathVariable int id, @RequestBody Role role){
        Response<Role> response = new Response<>();
        Role role1 = roleServiceImpl.updateRole(id, role);
        if(role1!=null){
            response.successResponse(role1,"updated successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.failureResponse("invalid Id");
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("role/{id}")
    public ResponseEntity<Response<String>> deleteRole(@PathVariable int id){
        Response<String> response = new Response<>();
        String data = roleServiceImpl.deleteRole(id);
        if(data.equals("success")){
            response.successResponse(data,"deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("invalid Id");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
