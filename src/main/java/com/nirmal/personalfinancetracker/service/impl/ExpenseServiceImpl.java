package com.nirmal.personalfinancetracker.service.impl;

import com.nirmal.personalfinancetracker.dto.request.AddExpenseDto;
import com.nirmal.personalfinancetracker.enums.ExpenseEnum;
import com.nirmal.personalfinancetracker.enums.RecurrenceEnum;
import com.nirmal.personalfinancetracker.model.Expense;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.repository.ExpenseRepository;
import com.nirmal.personalfinancetracker.repository.UserRepository;
import com.nirmal.personalfinancetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BudgetLimitServiceImpl budgetLimitServiceImpl;
    @Autowired
    private GoalServiceImpl goalServiceImpl;

    @Override
    public Expense addExpense(AddExpenseDto addExpenseDto) {
        Optional<User> user = userRepository.findById(addExpenseDto.getUserId());
        Expense expense = new Expense();
        expense.setUser(user.get());
        expense.setCategory(addExpenseDto.getCategory());
        expense.setAmount(addExpenseDto.getAmount());
        expense.setDescription(addExpenseDto.getDescription());

        Expense expense1 = expenseRepository.save(expense);
//        if(expense.getAmount().compareTo(budgetLimitServiceImpl.getLimit(expense.getCategory(), expense.get)) > 0){
//            Pair<Expense, Boolean> expenseBooleanPair = Pair.of(expense, false); // Pair.of(Expense, underLimit)
//            return expenseBooleanPair;
//        }
//        Pair<Expense,Boolean> expenseBooleanPair = Pair.of(expense, true);
        return expense1;
    }

    @Override
    public Expense viewExpense(int expenseId) {
        return expenseRepository.findById(expenseId).get();
    }

    public Pair<Expense, Boolean> addExpense(Expense expense, int goalId) {
        expenseRepository.save(expense);
        goalServiceImpl.addAmountToGoal(expense.getAmount(), goalId);
        if(expense.getAmount().compareTo(budgetLimitServiceImpl.getLimit((expense.getCategory()))) > 0){
            Pair<Expense, Boolean> expenseBooleanPair = Pair.of(expense, false); // Pair.of(Expense, underLimit)
            return expenseBooleanPair;
        }

        Pair<Expense, Boolean> expenseBooleanPair = Pair.of(expense, true); // Pair.of(Expense, underLimit)
        return expenseBooleanPair;
    }

    @Override
    public List<Expense> viewExpenseList() {
        return expenseRepository.findAll();
    }

    @Override
    public Expense updateExpense(int expenseId, AddExpenseDto addExpenseDto) {
        Expense expense = expenseRepository.findById(expenseId).get();
        expense.setDescription(addExpenseDto.getDescription());
        expense.setUser(userRepository.findById(addExpenseDto.getUserId()).get());
        expense.setAmount(addExpenseDto.getAmount());
        expense.setCategory(addExpenseDto.getCategory());
        expenseRepository.save(expense);
        return expense;
    }

    @Override
    public String deleteExpense(int expenseId) {
        Optional<Expense> expense = expenseRepository.findById(expenseId);
        if(expense.isPresent()){
            expenseRepository.deleteById(expenseId);
            return "success";
        }
        return "expense with id: "+expenseId+ " not present.";
    }

    public BigDecimal totalExpenseInInterval(ExpenseEnum category, RecurrenceEnum interval){

        LocalDateTime start = null;
        LocalDateTime end = null;
        LocalDateTime now = LocalDateTime.now();
        switch (interval){
            case DAILY:
                start = now.withHour(0).withMinute(0).withSecond(0).withNano(0);
                end = now.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
                break;
            case WEEKLY:
                start = now.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0).withNano(0);
                end = start.plusDays(6).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
                break;
            case MONTHLY:
                start = now.with(TemporalAdjusters.firstDayOfMonth());
                end = now.with(TemporalAdjusters.lastDayOfMonth());
                break;
            case YEARLY:
                int year = now.getYear();
                start = LocalDateTime.of(year, Month.JANUARY, 1, 0, 0, 0);
                end = LocalDateTime.of(year, Month.DECEMBER, 31, 23, 59, 59);
                break;
        }
        List<Expense> expenses = expenseRepository.findTotalExpense(category,start,end);
        BigDecimal sum = BigDecimal.ZERO;
        for(Expense expense:expenses){
            sum = sum.add(expense.getAmount());
        }

    return sum;
    }
}
