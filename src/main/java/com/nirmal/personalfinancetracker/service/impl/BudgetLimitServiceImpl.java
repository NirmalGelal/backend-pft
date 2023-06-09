package com.nirmal.personalfinancetracker.service.impl;

import com.nirmal.personalfinancetracker.enums.ExpenseEnum;
import com.nirmal.personalfinancetracker.model.BudgetLimit;
import com.nirmal.personalfinancetracker.repository.BudgetLimitRepository;
import com.nirmal.personalfinancetracker.service.BudgetLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetLimitServiceImpl implements BudgetLimitService {
    @Autowired
    private BudgetLimitRepository budgetLimitRepository;
    @Override
    public BudgetLimit addBudgetLimit(BudgetLimit budgetLimit) {
        budgetLimitRepository.save(budgetLimit);
        return budgetLimit;
    }

    @Override
    public List<BudgetLimit> viewBudgetLimitList() {
        return budgetLimitRepository.findAll();
    }

    @Override
    public BudgetLimit updateBudgetLimit(BudgetLimit budgetLimit) {
        budgetLimitRepository.save(budgetLimit);
        return budgetLimit;
    }

    @Override
    public String deleteBudgetLimit(int budgetLimitId) {
        Optional<BudgetLimit> budgetLimit = budgetLimitRepository.findById(budgetLimitId);
        if(budgetLimit.isPresent()){
            budgetLimitRepository.deleteById(budgetLimitId);
            return "limit deleted successfully";
        }
        return "budget limit with id: "+budgetLimit.get().getId()+ " not present.";
    }

    public BigDecimal getLimit(ExpenseEnum category){
        BudgetLimit budgetLimit = budgetLimitRepository.findByCategory(category);
        return budgetLimit.getLimit();
    }
}
