package com.nirmal.personalfinancetracker.service.impl;

import com.nirmal.personalfinancetracker.dto.request.UserRequestDto;
import com.nirmal.personalfinancetracker.dto.response.*;
import com.nirmal.personalfinancetracker.model.*;
import com.nirmal.personalfinancetracker.repository.RoleRepository;
import com.nirmal.personalfinancetracker.service.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DtoMapperImpl implements DtoMapper {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public UserResponseDto toUserDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .roleId(user.getRoleId().getId())
                .build();
    }

    @Override
    public IncomeDto toIncomeDto(Income income) {
        return IncomeDto.builder()
                .id(income.getId())
                .userId(income.getUser().getId())
                .amount(income.getAmount())
                .category(income.getCategory())
                .description(income.getDescription())
                .build();
    }

    @Override
    public BudgetLimitResponseDto toBudgetLimitDto(BudgetLimit budgetLimit) {
        return BudgetLimitResponseDto.builder()
                .id(budgetLimit.getId())
                .userId(budgetLimit.getUser().getId())
                .limit(budgetLimit.getLimit())
                .category(budgetLimit.getCategory())
                .interval(budgetLimit.getInterval())
                .build();
    }

    @Override
    public GoalDto toGoalDto(Goal goal) {
        return GoalDto.builder()
                .id(goal.getId())
                .userId(goal.getUser().getId())
                .name(goal.getName())
                .totalAmount(goal.getTotalAmount())
                .amountSaved(goal.getAmountSaved())
                .status(goal.getStatus())
                .build();
    }

    @Override
    public RecurringBillsResponseDto toRecurringBillsDto(RecurringBills recurringBills) {
        return RecurringBillsResponseDto.builder()
                .id(recurringBills.getId())
                .userId(recurringBills.getUser().getId())
                .name(recurringBills.getName())
                .recurrence(recurringBills.getRecurrence())
                .amount(recurringBills.getAmount())
                .build();
    }

    @Override
    public ExpenseResponseDto toExpenseDto(Expense expense) {
        return ExpenseResponseDto.builder()
                .id(expense.getId())
                .userId(expense.getUser().getId())
                .amount(expense.getAmount())
                .category(expense.getCategory())
                .description(expense.getDescription())
                .build();
    }

    @Override
    public GoalExpenseResponseDto toGoalExpenseDto(Expense expense, Goal goal, String data) {
        return GoalExpenseResponseDto.builder()
                .goalId(goal.getId())
                .expenseId(expense.getId())
                .amount(expense.getAmount())
                .build();
    }

    @Override
    public User toUserEntity(UserRequestDto userRequestDto) {
        User user = new User();
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setRoleId(roleRepository.findByRole(userRequestDto.getRole()));
        user.setPhoneNumber(userRequestDto.getPhoneNumber());
        user.setAddress(userRequestDto.getAddress());
        return user;
    }
}
