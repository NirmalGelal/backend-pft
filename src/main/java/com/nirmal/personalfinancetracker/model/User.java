package com.nirmal.personalfinancetracker.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nirmal.personalfinancetracker.enums.RoleEnum;
import jakarta.persistence.*;
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
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private int id;

    @Column(name = "usr_name")
    private String name;

    @Column(name = "usr_email")
    private String email;

    @Column(name = "usr_password")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rl_id")
    private Role roleId;

    @Column(name = "usr_phone_number")
    private String phoneNumber;

    @Column(name = "usr_address")
    private String address;
}
