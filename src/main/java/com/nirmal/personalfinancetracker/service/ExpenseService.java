package com.nirmal.personalfinancetracker.service;

import com.nirmal.personalfinancetracker.dto.request.AddExpenseDto;
import com.nirmal.personalfinancetracker.dto.response.ExpenseResponseDto;
import com.nirmal.personalfinancetracker.model.Expense;

import java.util.List;

public interface ExpenseService {

    public ExpenseResponseDto addExpense(AddExpenseDto expense);
    public Expense viewExpense(int expenseId);
    public List<Expense> viewExpenseList();
    public ExpenseResponseDto updateExpense(int expenseId, AddExpenseDto addExpenseDto);
    public ExpenseResponseDto updateGoalExpense(int goalId, int expenseId, AddExpenseDto addExpenseDto);
    public String deleteExpense(int expenseId);
    public String deleteGoalExpense(int goalId, int expenseId);
}
