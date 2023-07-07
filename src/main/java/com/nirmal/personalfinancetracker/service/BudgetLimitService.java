package com.nirmal.personalfinancetracker.service;

import com.nirmal.personalfinancetracker.dto.request.BudgetLimitRequestDto;
import com.nirmal.personalfinancetracker.dto.response.BudgetLimitResponseDto;

import java.util.List;

public interface BudgetLimitService {
    public BudgetLimitResponseDto addBudgetLimit(BudgetLimitRequestDto budgetLimitRequestDto);
    public List<BudgetLimitResponseDto> viewBudgetLimitList();
    public BudgetLimitResponseDto viewBudgetLimitById(int budgetLimitId);
    public BudgetLimitResponseDto updateBudgetLimit(int budgetLimitId, BudgetLimitRequestDto budgetLimitRequestDto);
    public String deleteBudgetLimit(int budgetLimitId);
}
