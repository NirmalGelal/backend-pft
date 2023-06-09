package com.nirmal.personalfinancetracker.dto.request;

import com.nirmal.personalfinancetracker.enums.ExpenseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddExpenseDto {
    private int userId;
    private BigDecimal amount;
    private ExpenseEnum category;
    private String description;
}
