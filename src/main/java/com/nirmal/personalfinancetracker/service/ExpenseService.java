package com.nirmal.personalfinancetracker.service;

import com.nirmal.personalfinancetracker.dto.request.AddExpenseDto;
import com.nirmal.personalfinancetracker.dto.request.AddGoalExpenseDto;
import com.nirmal.personalfinancetracker.dto.response.ExpenseResponseDto;
import com.nirmal.personalfinancetracker.dto.response.GoalExpenseResponseDto;
import com.nirmal.personalfinancetracker.model.Expense;

import java.util.List;

public interface ExpenseService {

    public ExpenseResponseDto addExpense(AddExpenseDto expense);
    public ExpenseResponseDto viewExpense(int expenseId);
    public List<ExpenseResponseDto> viewExpenseList();
    public ExpenseResponseDto updateExpense(int expenseId, AddExpenseDto addExpenseDto);
    public GoalExpenseResponseDto updateGoalExpense(int expenseId, AddGoalExpenseDto addGoalExpenseDto);
    public String deleteExpense(int expenseId);
    public String deleteGoalExpense(int goalId, int expenseId);
}
