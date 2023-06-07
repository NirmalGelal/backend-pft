package com.nirmal.personalfinancetracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ftr_goal")
public class Goal {

    @Id
    @Column(name = "gl_id")
    private int id;

    @OneToOne (fetch = FetchType.EAGER)
    @Column(name = "usr_id")
    private User user;

    @Column(name = "gl_goal_name")
    private String name;

    @Column(name = "gl_total_amount")
    private BigDecimal totalAmount;

    @Column(name = "gl_amount_saved")
    private BigDecimal amountSaved;
}
