package com.nirmal.personalfinancetracker.dto.request;

import com.nirmal.personalfinancetracker.enums.IncomeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddIncomeDto {
    private int userId;
    private BigDecimal amount;
    private IncomeEnum category;
    private String description;
}
