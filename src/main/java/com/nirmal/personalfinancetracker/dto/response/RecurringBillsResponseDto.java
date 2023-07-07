package com.nirmal.personalfinancetracker.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nirmal.personalfinancetracker.enums.RecurrenceEnum;
import com.nirmal.personalfinancetracker.model.RecurringBills;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RecurringBillsResponseDto {
    private int id;
    private int userId;
    private String name;
    private RecurrenceEnum recurrence;
    private BigDecimal amount;
}
