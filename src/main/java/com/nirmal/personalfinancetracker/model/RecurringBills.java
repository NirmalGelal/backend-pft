package com.nirmal.personalfinancetracker.model;

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
@Table(name = "ftr_recurring_bills")
public class RecurringBills {
    @Id
    @Column(name = "rb_id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @Column(name = "usr_id")
    private User user;

    @Column(name = "rb_name")
    private String name;

    @Column(name = "rb_recurrence")
    private RecurrenceEnum recurrence;

    @Column(name = "rb_amount")
    private BigDecimal amount;

}
