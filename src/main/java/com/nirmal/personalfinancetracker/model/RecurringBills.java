package com.nirmal.personalfinancetracker.model;

import com.nirmal.personalfinancetracker.enums.RecurrenceEnum;
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
@Table(name = "ftr_recurring_bills")
public class RecurringBills {
    @Id
    @Column(name = "rb_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usr_id")
    private User user;

    @Column(name = "rb_name")
    private String name;

    @Column(name = "rb_recurrence")
    @Enumerated(EnumType.STRING)
    private RecurrenceEnum recurrence;

    @Column(name = "rb_amount")
    private BigDecimal amount;

    @Column(name = "rb_created_at")
    private LocalDateTime createdAt;

}
