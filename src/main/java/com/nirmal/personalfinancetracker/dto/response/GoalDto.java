package com.nirmal.personalfinancetracker.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public class GoalDto {
    private int id;
    private int userId;
    private String name;
    private BigDecimal totalAmount;
    private BigDecimal amountSaved;
    private String status;
}
