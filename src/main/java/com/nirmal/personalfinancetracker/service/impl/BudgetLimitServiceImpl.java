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
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Override
    public BudgetLimitResponseDto addBudgetLimit(BudgetLimitRequestDto budgetLimitRequestDto) {
        User user = userServiceImpl.getCurrentUser();
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
        User user = userServiceImpl.getCurrentUser();
        List<BudgetLimit> budgetLimitList = budgetLimitRepository.findAllByUserId(user.getId());
        List<BudgetLimitResponseDto> budgetLimitResponseDtos = new ArrayList<>();
            for (BudgetLimit budgetLimit:
                 budgetLimitList) {
                budgetLimitResponseDtos.add(dtoMapper.toBudgetLimitDto(budgetLimit));
            }
            return budgetLimitResponseDtos;

    }

    @Override
    public BudgetLimitResponseDto viewBudgetLimitById(int budgetLimitId) {
        User user= userServiceImpl.getCurrentUser();
        Optional<BudgetLimit> budgetLimitOptional = budgetLimitRepository.findById(budgetLimitId);
        if(budgetLimitOptional.isPresent() && budgetLimitOptional.get().getUser().getId() == user.getId()){
            return dtoMapper.toBudgetLimitDto(budgetLimitOptional.get());
        }
        return null;
    }

    @Override
    public BudgetLimitResponseDto updateBudgetLimit(int budgetLimitId, BudgetLimitRequestDto budgetLimitRequestDto) {
        Optional<BudgetLimit> budgetLimitOptional =budgetLimitRepository.findById(budgetLimitId);
        User user = userServiceImpl.getCurrentUser();
        if(budgetLimitOptional.isPresent() && user.getId() == budgetLimitOptional.get().getUser().getId()){
            BudgetLimit budgetLimit = budgetLimitOptional.get();
            budgetLimit.setId((budgetLimitId));
            budgetLimit.setUser(user);
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
        User user = userServiceImpl.getCurrentUser();
        if(budgetLimit.isPresent() && budgetLimit.get().getUser().getId() == user.getId()){
            budgetLimitRepository.deleteById(budgetLimitId);
            return "success";
        }
        return "user not authorized";
    }

    public BigDecimal getLimit(ExpenseEnum category, RecurrenceEnum interval){
        Optional<BudgetLimit> budgetLimit = budgetLimitRepository.findByCategoryAndInterval(category,interval);
        return budgetLimit.map(BudgetLimit::getLimit).orElse(null);
    }
}
