package com.nirmal.personalfinancetracker.dto.request;

import com.nirmal.personalfinancetracker.enums.ExpenseEnum;
import com.nirmal.personalfinancetracker.enums.RecurrenceEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetLimitRequestDto {
    private int userId;
    private BigDecimal limit;
    private ExpenseEnum category;
    private RecurrenceEnum interval;
}
