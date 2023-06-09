package com.nirmal.personalfinancetracker.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddGoalDto {
    private int userId;
    private String name;
    private BigDecimal totalAmount;

}
