package com.nirmal.personalfinancetracker.service;

import com.nirmal.personalfinancetracker.model.Role;

import java.util.List;

public interface RoleService {
    public Role addRole(Role role);
    public List<Role> viewRoleList();
    public Role viewRoleById(int roleId);
    public Role updateRole(int roleId, Role role);
    public String deleteRole(int roleId);

}
