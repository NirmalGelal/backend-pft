package com.nirmal.personalfinancetracker.model;

import com.nirmal.personalfinancetracker.enums.ExpenseEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ftr_expense")
public class Expense {
    @Id
    @Column(name = "exp_id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @Column(name = "usr_id")
    private User user;

    @Column(name = "exp_amount")
    private BigDecimal amount;

    @Column(name = "exp_category")
    private ExpenseEnum category;

    @Column(name = "exp_date")
    private LocalDateTime date;

    @Column(name = "exp_description")
    private String description;
}
