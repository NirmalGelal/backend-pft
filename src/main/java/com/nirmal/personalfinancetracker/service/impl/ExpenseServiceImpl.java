package com.nirmal.personalfinancetracker.service.impl;

import com.nirmal.personalfinancetracker.dto.request.AddExpenseDto;
import com.nirmal.personalfinancetracker.dto.response.ExpenseResponseDto;
import com.nirmal.personalfinancetracker.dto.response.GoalExpenseResponseDto;
import com.nirmal.personalfinancetracker.enums.ExpenseEnum;
import com.nirmal.personalfinancetracker.enums.RecurrenceEnum;
import com.nirmal.personalfinancetracker.model.Expense;
import com.nirmal.personalfinancetracker.model.Goal;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.repository.ExpenseRepository;
import com.nirmal.personalfinancetracker.repository.GoalRepository;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GoalRepository goalRepository;
    @Autowired
    private BudgetLimitServiceImpl budgetLimitServiceImpl;
    @Autowired
    private GoalServiceImpl goalServiceImpl;
    @Autowired
    private DtoMapperImpl dtoMapper;
    @Override
    public ExpenseResponseDto addExpense(AddExpenseDto addExpenseDto) {
        Optional<User> user = userRepository.findById(addExpenseDto.getUserId());
        Expense expense = new Expense();
        expense.setUser(user.get());
        expense.setCategory(addExpenseDto.getCategory());
        expense.setAmount(addExpenseDto.getAmount());
        expense.setDescription(addExpenseDto.getDescription());

        expenseRepository.save(expense);
        return dtoMapper.toExpenseDto(expense);
    }

    public GoalExpenseResponseDto addExpense(int goalId, AddExpenseDto addExpenseDto) {
        Optional<User> user = userRepository.findById(addExpenseDto.getUserId());
        Expense expense = new Expense();
        expense.setUser(user.get());
        expense.setCategory(addExpenseDto.getCategory());
        expense.setDescription(addExpenseDto.getDescription());
        expense.setAmount(addExpenseDto.getAmount());

        expenseRepository.save(expense);
        String data = goalServiceImpl.addAmountToGoal(expense.getAmount(), goalId);
        Optional<Goal> goalOptional = goalRepository.findById(goalId);

        return dtoMapper.toGoalExpenseDto(expense,goalOptional.get(),data);

    }
    @Override
    public ExpenseResponseDto viewExpense(int expenseId) {
        Optional<Expense> expenseOptional = expenseRepository.findById(expenseId);
        return expenseOptional.map(expense -> dtoMapper.toExpenseDto(expense)).orElse(null);
    }

    @Override
    public List<ExpenseResponseDto> viewExpenseList() {
        List<Expense> expenses = expenseRepository.findAll();
        List<ExpenseResponseDto> expenseResponseDtos = new ArrayList<>();
        for (Expense expense:
             expenses) {
            expenseResponseDtos.add(dtoMapper.toExpenseDto(expense));
        }
        return expenseResponseDtos;
    }

    @Override
    public ExpenseResponseDto updateExpense(int expenseId, AddExpenseDto addExpenseDto) {
        Optional<User> user = userRepository.findById(addExpenseDto.getUserId());

        Expense expense = expenseRepository.findById(expenseId).get();
        expense.setUser(user.get());
        expense.setCategory(addExpenseDto.getCategory());
        expense.setDescription(addExpenseDto.getDescription());
        expense.setAmount(addExpenseDto.getAmount());
        expenseRepository.save(expense);

        ExpenseResponseDto expenseResponseDto = dtoMapper.toExpenseDto(expense);
        expenseResponseDto.setOverLimit(createHashMap(expense));
        return expenseResponseDto;
    }

    @Override
    public GoalExpenseResponseDto updateGoalExpense(int goalId, int expenseId, AddExpenseDto addExpenseDto) {
        Optional<Expense> expenseOptional = expenseRepository.findById(expenseId);
        Optional<User> userOptional = userRepository.findById(addExpenseDto.getUserId());
        Optional<Goal> goalOptional = goalRepository.findById(goalId);
        if(expenseOptional.isPresent() && goalOptional.isPresent() && userOptional.isPresent()){

            BigDecimal oldAmount = expenseOptional.get().getAmount();
            BigDecimal newAmount = addExpenseDto.getAmount();
            goalOptional.get().setAmountSaved(goalOptional.get().getAmountSaved().subtract(oldAmount).add(newAmount));
            goalRepository.save(goalOptional.get());

            Expense expense = expenseOptional.get();
            expense.setUser(userOptional.get());
            expense.setCategory(addExpenseDto.getCategory());
            expense.setDescription(addExpenseDto.getDescription());
            expense.setAmount(addExpenseDto.getAmount());
            expenseRepository.save(expenseOptional.get());

            GoalExpenseResponseDto goalExpenseResponseDto = dtoMapper.toGoalExpenseDto(expense,goalOptional.get(),"");
            goalExpenseResponseDto.setOverLimit(createHashMap(expenseOptional.get()));
            return goalExpenseResponseDto;
        }
        return null;
    }

    @Override
    public String deleteExpense(int expenseId) {
        Optional<Expense> expense = expenseRepository.findById(expenseId);
        if(expense.isPresent()){
            if(expense.get().getCategory()!=ExpenseEnum.GOAL){
                expenseRepository.deleteById(expenseId);
                return "success";
            }
            else {
                return "goal id not given";
            }
        }
        return "expense with id: "+expenseId+ " not present.";
    }

    @Override
    public String deleteGoalExpense(int goalId, int expenseId) {
        Optional<Expense> expense = expenseRepository.findById(expenseId);
        Optional<Goal> goal = goalRepository.findById(goalId);
        if(expense.isPresent() && goal.isPresent()){
            BigDecimal amountReduced = expense.get().getAmount();
            goal.get().setAmountSaved(goal.get().getAmountSaved().subtract(amountReduced));
            goalRepository.save(goal.get());
            expenseRepository.deleteById(expenseId);
            return "success";
        }
        return "expense with id: "+expenseId+" or goal with id: "+ goalId +" not present";
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

    public List<Boolean> checkLimitInInterval(Expense expense){
        List<Boolean> checkLimitInInterval = new ArrayList<>();
        for(RecurrenceEnum recurrenceEnum: RecurrenceEnum.values()){
            BigDecimal totalExpenses = totalExpenseInInterval(expense.getCategory(),recurrenceEnum);
            BigDecimal limit = budgetLimitServiceImpl.getLimit(expense.getCategory(),recurrenceEnum);
            if(limit!=null) {
                if(totalExpenses.compareTo(limit) > 0){
                    checkLimitInInterval.add(true);
                    continue;
                }
                else {
                    checkLimitInInterval.add(false);
                    continue;
                }
            }
            checkLimitInInterval.add(null);
        }
        return checkLimitInInterval;
    }

    public HashMap<RecurrenceEnum,Boolean> createHashMap(Expense expense){
        List<Boolean> checkLimitInInterval = checkLimitInInterval(expense);
        RecurrenceEnum[] interval = RecurrenceEnum.values();
        HashMap<RecurrenceEnum,Boolean> overLimit = new HashMap<>();
        for(int i=0; i<interval.length;i++){
            overLimit.put(interval[i], checkLimitInInterval.get(i));
        }
        return overLimit;
    }
}
