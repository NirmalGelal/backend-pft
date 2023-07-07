package com.nirmal.personalfinancetracker.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nirmal.personalfinancetracker.enums.IncomeEnum;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class IncomeDto {
    private int id;
    private int userId;
    private BigDecimal amount;
    private IncomeEnum category;
    private String description;
}
