package com.nirmal.personalfinancetracker.service.impl;

import com.nirmal.personalfinancetracker.dto.response.*;
import com.nirmal.personalfinancetracker.model.*;
import com.nirmal.personalfinancetracker.service.DtoMapper;
import org.springframework.stereotype.Service;

@Service
public class DtoMapperImpl implements DtoMapper {
    @Override
    public UserDto toUserDto(User user) {
        return UserDto.builder()
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
                .data(data)
                .build();
    }
}
