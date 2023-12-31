package com.nirmal.personalfinancetracker.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exp_id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usr_id")
    private User user;

    @Column(name = "exp_amount")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "exp_category")
    private ExpenseEnum category;

    @Column(name = "exp_created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "exp_description")
    private String description;
}
