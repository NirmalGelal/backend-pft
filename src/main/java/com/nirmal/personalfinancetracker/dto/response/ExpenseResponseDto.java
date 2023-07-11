package com.nirmal.personalfinancetracker.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nirmal.personalfinancetracker.enums.ExpenseEnum;
import com.nirmal.personalfinancetracker.enums.RecurrenceEnum;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashMap;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExpenseResponseDto {
    private int id;
    private int userId;
    private BigDecimal amount;
    private ExpenseEnum category;
    private String description;
    private HashMap<RecurrenceEnum,Boolean> overLimit = new HashMap<>();
}
