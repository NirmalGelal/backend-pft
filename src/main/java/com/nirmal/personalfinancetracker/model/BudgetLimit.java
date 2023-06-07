package com.nirmal.personalfinancetracker.model;

import com.nirmal.personalfinancetracker.enums.ExpenseEnum;
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
@Table(name = "ftr_budget_limit")
public class BudgetLimit {

    @Id
    @Column(name = "bl_id")
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @Column(name = "usr_id")
    private User user;

    @Column(name = "bl_limit")
    private BigDecimal limit;

    @Column(name = "exp_category")
    private ExpenseEnum category;
}
