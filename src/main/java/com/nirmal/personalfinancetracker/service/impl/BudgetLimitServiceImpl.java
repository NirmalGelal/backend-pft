package com.nirmal.personalfinancetracker.service.impl;

import com.nirmal.personalfinancetracker.dto.request.BudgetLimitRequestDto;
import com.nirmal.personalfinancetracker.dto.response.BudgetLimitResponseDto;
import com.nirmal.personalfinancetracker.enums.ExpenseEnum;
import com.nirmal.personalfinancetracker.enums.RecurrenceEnum;
import com.nirmal.personalfinancetracker.model.BudgetLimit;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.repository.BudgetLimitRepository;
import com.nirmal.personalfinancetracker.repository.UserRepository;
import com.nirmal.personalfinancetracker.service.BudgetLimitService;
import com.nirmal.personalfinancetracker.service.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetLimitServiceImpl implements BudgetLimitService {
    @Autowired
    private BudgetLimitRepository budgetLimitRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DtoMapper dtoMapper;
    @Override
    public BudgetLimitResponseDto addBudgetLimit(BudgetLimitRequestDto budgetLimitRequestDto) {
        User user = userRepository.findById(budgetLimitRequestDto.getUserId()).get();
        BudgetLimit budgetLimit = new BudgetLimit();
        budgetLimit.setLimit(budgetLimitRequestDto.getLimit());
        budgetLimit.setUser(user);
        budgetLimit.setCategory(budgetLimitRequestDto.getCategory());
        budgetLimit.setInterval(budgetLimitRequestDto.getInterval());
        budgetLimitRepository.save(budgetLimit);
        return dtoMapper.toBudgetLimitDto(budgetLimit);
    }

    @Override
    public List<BudgetLimitResponseDto> viewBudgetLimitList() {
        List<BudgetLimit> budgetLimitList = budgetLimitRepository.findAll();
        List<BudgetLimitResponseDto> budgetLimitResponseDtos = new ArrayList<>();
        if(!budgetLimitList.isEmpty()){
            for (BudgetLimit budgetLimit:
                 budgetLimitList) {
                budgetLimitResponseDtos.add(dtoMapper.toBudgetLimitDto(budgetLimit));
            }
            return budgetLimitResponseDtos;
        }
        return null;
    }

    @Override
    public BudgetLimitResponseDto viewBudgetLimitById(int budgetLimitId) {
        Optional<BudgetLimit> budgetLimitOptional = budgetLimitRepository.findById(budgetLimitId);
        return budgetLimitOptional.map(budgetLimit -> dtoMapper.toBudgetLimitDto(budgetLimit)).orElse(null);
    }

    @Override
    public BudgetLimitResponseDto updateBudgetLimit(int budgetLimitId, BudgetLimitRequestDto budgetLimitRequestDto) {
        Optional<BudgetLimit> budgetLimitOptional =budgetLimitRepository.findById(budgetLimitId);
        if(budgetLimitOptional.isPresent()){
            BudgetLimit budgetLimit = budgetLimitOptional.get();
            budgetLimit.setId((budgetLimitId));
            budgetLimit.setUser(userRepository.findById(budgetLimitRequestDto.getUserId()).get());
            budgetLimit.setCategory(budgetLimitRequestDto.getCategory());
            budgetLimit.setLimit(budgetLimitRequestDto.getLimit());
            budgetLimit.setInterval(budgetLimitRequestDto.getInterval());
            budgetLimitRepository.save(budgetLimit);
            return dtoMapper.toBudgetLimitDto(budgetLimit);
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
        return budgetLimit.map(BudgetLimit::getLimit).orElse(null);
    }
}
