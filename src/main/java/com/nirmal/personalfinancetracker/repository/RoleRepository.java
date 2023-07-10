package com.nirmal.personalfinancetracker.repository;

import com.nirmal.personalfinancetracker.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByRole(String role);
}
