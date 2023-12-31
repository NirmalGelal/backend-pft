package com.nirmal.personalfinancetracker.service;

import com.nirmal.personalfinancetracker.dto.request.UserRequestDto;
import com.nirmal.personalfinancetracker.dto.response.*;
import com.nirmal.personalfinancetracker.model.*;

public interface DtoMapper {
    public UserResponseDto toUserDto(User user);
    public IncomeDto toIncomeDto(Income income);
    public BudgetLimitResponseDto toBudgetLimitDto(BudgetLimit budgetLimit);
    public GoalDto toGoalDto(Goal goal);
    public RecurringBillsResponseDto toRecurringBillsDto(RecurringBills recurringBills);
    public ExpenseResponseDto toExpenseDto(Expense expense);
    public GoalExpenseResponseDto toGoalExpenseDto(Expense expense, Goal goal, String data);
    public User toUserEntity(UserRequestDto userRequestDto);
}
