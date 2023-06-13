package com.nirmal.personalfinancetracker.service.impl;

import com.nirmal.personalfinancetracker.model.Role;
import com.nirmal.personalfinancetracker.repository.RoleRepository;
import com.nirmal.personalfinancetracker.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role addRole(Role role) {
        Role role1 = roleRepository.save(role);
        return role1;
    }

    @Override
    public List<Role> viewRoleList() {
        return roleRepository.findAll();
    }

    @Override
    public Role viewRoleById(int roleId) {
        return roleRepository.findById(roleId).get();
    }

    @Override
    public Role updateRole(int roleId, Role role) {
        Role role1 = roleRepository.findById(roleId).get();
        role1.setRole(role.getRole());
        roleRepository.save(role1);
        return role1;
    }

    @Override
    public String deleteRole(int roleId) {
        Optional<Role> roleOptional = roleRepository.findById(roleId);
        if(roleOptional.isPresent()){
            roleRepository.deleteById(roleId);
            return "success";
        }
        return "role with id: "+roleId+ " not present.";
    }


}
