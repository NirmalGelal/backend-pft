package com.nirmal.personalfinancetracker.service;

import com.nirmal.personalfinancetracker.model.BudgetLimit;

import java.util.List;

public interface BudgetLimitService {
    public BudgetLimit addBudgetLimit(BudgetLimit budgetLimit);
    public List<BudgetLimit> viewBudgetLimitList();
    public BudgetLimit updateBudgetLimit(BudgetLimit budgetLimit);
    public String deleteBudgetLimit(int budgetLimitId);
}
