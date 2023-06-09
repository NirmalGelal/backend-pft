package com.nirmal.personalfinancetracker.model;

import com.nirmal.personalfinancetracker.enums.ExpenseEnum;
import com.nirmal.personalfinancetracker.enums.RecurrenceEnum;
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
@Table(name = "ftr_budget_limit", uniqueConstraints = {@UniqueConstraint(name = "categoryAndInterval",
        columnNames = {"exp_category","bl_interval"})})
public class BudgetLimit {

    @Id
    @Column(name = "bl_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usr_id")
    private User user;

    @Column(name = "bl_limit")
    private BigDecimal limit;

    @Column(name = "exp_category")
    @Enumerated(EnumType.STRING)
    private ExpenseEnum category;

    @Column(name = "bl_interval")
    @Enumerated(EnumType.STRING)
    private RecurrenceEnum interval;
}
