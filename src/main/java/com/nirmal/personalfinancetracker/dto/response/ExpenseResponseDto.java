package com.nirmal.personalfinancetracker.dto.response;

import com.nirmal.personalfinancetracker.enums.RecurrenceEnum;
import com.nirmal.personalfinancetracker.model.Expense;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponseDto {
    private Expense expense;
    private HashMap<RecurrenceEnum,Boolean> overLimit = new HashMap<>();
}
