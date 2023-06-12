package com.nirmal.personalfinancetracker.service;

import com.nirmal.personalfinancetracker.dto.request.BudgetLimitDto;
import com.nirmal.personalfinancetracker.model.BudgetLimit;

import java.util.List;

public interface BudgetLimitService {
    public BudgetLimit addBudgetLimit(BudgetLimitDto budgetLimitDto);
    public List<BudgetLimit> viewBudgetLimitList();
    public BudgetLimit viewBudgetLimitById(int budgetLimitId);
    public BudgetLimit updateBudgetLimit(int budgetLimitId, BudgetLimitDto budgetLimitDto);
    public String deleteBudgetLimit(int budgetLimitId);
}
