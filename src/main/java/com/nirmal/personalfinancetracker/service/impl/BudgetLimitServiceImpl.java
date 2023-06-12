package com.nirmal.personalfinancetracker.service.impl;

import com.nirmal.personalfinancetracker.dto.request.BudgetLimitDto;
import com.nirmal.personalfinancetracker.enums.ExpenseEnum;
import com.nirmal.personalfinancetracker.enums.RecurrenceEnum;
import com.nirmal.personalfinancetracker.model.BudgetLimit;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.repository.BudgetLimitRepository;
import com.nirmal.personalfinancetracker.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;
    @Override
    public BudgetLimit addBudgetLimit(BudgetLimitDto budgetLimitDto) {
        User user = userRepository.findById(budgetLimitDto.getUserId()).get();
        BudgetLimit budgetLimit = new BudgetLimit();
        budgetLimit.setLimit(budgetLimitDto.getLimit());
        budgetLimit.setUser(user);
        budgetLimit.setCategory(budgetLimitDto.getCategory());
        budgetLimit.setInterval(budgetLimitDto.getInterval());
        budgetLimitRepository.save(budgetLimit);
        return budgetLimit;
    }

    @Override
    public List<BudgetLimit> viewBudgetLimitList() {
        List<BudgetLimit> budgetLimitList = budgetLimitRepository.findAll();
        if(!budgetLimitList.isEmpty()){
            return budgetLimitRepository.findAll();
        }
        return null;
    }

    @Override
    public BudgetLimit viewBudgetLimitById(int budgetLimitId) {
        Optional<BudgetLimit> budgetLimitOptional = budgetLimitRepository.findById(budgetLimitId);
        if(budgetLimitOptional.isPresent()){
            return budgetLimitOptional.get();
        }
        return null;
    }

    @Override
    public BudgetLimit updateBudgetLimit(int budgetLimitId,BudgetLimitDto budgetLimitDto) {
        Optional<BudgetLimit> budgetLimitOptional =budgetLimitRepository.findById(budgetLimitId);
        if(budgetLimitOptional.isPresent()){
            BudgetLimit budgetLimit = budgetLimitOptional.get();
            budgetLimit.setId((budgetLimitId));
            budgetLimit.setUser(userRepository.findById(budgetLimitDto.getUserId()).get());
            budgetLimit.setCategory(budgetLimitDto.getCategory());
            budgetLimit.setLimit(budgetLimitDto.getLimit());
            budgetLimit.setInterval(budgetLimitDto.getInterval());
            budgetLimitRepository.save(budgetLimit);
            return budgetLimit;
        }
        return null;
    }

    @Override
    public String deleteBudgetLimit(int budgetLimitId) {
        Optional<BudgetLimit> budgetLimit = budgetLimitRepository.findById(budgetLimitId);
        if(budgetLimit.isPresent()){
            budgetLimitRepository.deleteById(budgetLimitId);
            return "success";
        }
        return "budget limit with id: "+budgetLimitId+ " not present.";
    }

    public BigDecimal getLimit(ExpenseEnum category, RecurrenceEnum interval){
        Optional<BudgetLimit> budgetLimit = budgetLimitRepository.findByCategoryAndInterval(category,interval);
        if(budgetLimit.isPresent()){
            return budgetLimit.get().getLimit();
        }
        return null;
    }
}
