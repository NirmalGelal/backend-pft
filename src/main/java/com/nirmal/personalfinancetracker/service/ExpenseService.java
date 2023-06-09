package com.nirmal.personalfinancetracker.service;

import com.nirmal.personalfinancetracker.dto.request.AddExpenseDto;
import com.nirmal.personalfinancetracker.model.Expense;
import org.springframework.data.util.Pair;

import java.util.List;

public interface ExpenseService {

    public Expense addExpense(AddExpenseDto expense);
    public Expense viewExpense(int expenseId);
    public List<Expense> viewExpenseList();
    public Expense updateExpense(int expenseId, AddExpenseDto addExpenseDto);
    public String deleteExpense(int expenseId);
}
