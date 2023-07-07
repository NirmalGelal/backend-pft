package com.nirmal.personalfinancetracker.dto.request;

import com.nirmal.personalfinancetracker.enums.RecurrenceEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecurringBillsRequestDto {
    private int userId;
    private String name;
    private BigDecimal amount;
    private RecurrenceEnum interval;
}
