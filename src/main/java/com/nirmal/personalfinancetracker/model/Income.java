package com.nirmal.personalfinancetracker.model;

import com.nirmal.personalfinancetracker.enums.IncomeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ftr_income")
public class Income {
    @Id
    @Column(name = "inc_id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @Column(name = "usr_id")
    private User user;

    @Column(name = "inc_amount")
    private BigDecimal amount;

    @Column(name = "inc_category")
    private IncomeEnum category;

    @Column(name = "inc_date")
    private LocalDateTime date;

    @Column(name = "inc_description")
    private String description;
}
