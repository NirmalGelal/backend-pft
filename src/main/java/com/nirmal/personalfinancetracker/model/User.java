package com.nirmal.personalfinancetracker.model;

import com.nirmal.personalfinancetracker.enums.RoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ftr_user")
public class User {
    @Id
    @Column(name = "usr_id")
    private int id;

    @Column(name = "usr_name")
    private String name;

    @Column(name = "usr_email")
    private String email;

    @Column(name = "usr_password")
    private String password;

    @Column(name = "usr_role")
    private RoleEnum role;

    @Column(name = "usr_phone_number")
    private String phoneNumber;

    @Column(name = "usr_address")
    private String address;
}
