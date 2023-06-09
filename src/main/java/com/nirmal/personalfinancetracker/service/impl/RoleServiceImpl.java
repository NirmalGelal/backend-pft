package com.nirmal.personalfinancetracker.service.impl;

import com.nirmal.personalfinancetracker.model.Role;
import com.nirmal.personalfinancetracker.repository.RoleRepository;
import com.nirmal.personalfinancetracker.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role addRole(Role role) {
        Role role1 = roleRepository.save(role);
        return role1;
    }
}
