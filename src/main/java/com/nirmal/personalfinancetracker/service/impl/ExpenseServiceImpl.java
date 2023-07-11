package com.nirmal.personalfinancetracker.service.impl;

import com.nirmal.personalfinancetracker.dto.request.AddExpenseDto;
import com.nirmal.personalfinancetracker.dto.request.AddGoalExpenseDto;
import com.nirmal.personalfinancetracker.dto.response.ExpenseResponseDto;
import com.nirmal.personalfinancetracker.dto.response.GoalExpenseResponseDto;
import com.nirmal.personalfinancetracker.enums.ExpenseEnum;
import com.nirmal.personalfinancetracker.enums.RecurrenceEnum;
import com.nirmal.personalfinancetracker.model.Expense;
import com.nirmal.personalfinancetracker.model.Goal;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.repository.ExpenseRepository;
import com.nirmal.personalfinancetracker.repository.GoalRepository;
import com.nirmal.personalfinancetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserServiceImpl userServiceImpl;
    @Autowired
    private BudgetLimitServiceImpl budgetLimitServiceImpl;
    @Autowired
    private GoalRepository goalRepository;
    @Autowired
    private GoalServiceImpl goalServiceImpl;
    @Autowired
    private DtoMapperImpl dtoMapper;
    @Override
    public ExpenseResponseDto addExpense(AddExpenseDto addExpenseDto) {
        User user = userServiceImpl.getCurrentUser();
        Expense expense = new Expense();
        expense.setUser(user);
        expense.setCategory(addExpenseDto.getCategory());
        expense.setAmount(addExpenseDto.getAmount());
        expense.setDescription(addExpenseDto.getDescription());

        expenseRepository.save(expense);
        ExpenseResponseDto expenseResponseDto = dtoMapper.toExpenseDto(expense);
        expenseResponseDto.setOverLimit(createHashMap(expense));
        return expenseResponseDto;
    }

    public GoalExpenseResponseDto addExpense(int goalId, AddExpenseDto addExpenseDto) {
        User user = userServiceImpl.getCurrentUser();
        Expense expense = new Expense();
        expense.setUser(user);
        expense.setCategory(addExpenseDto.getCategory());
        expense.setDescription(addExpenseDto.getDescription());
        expense.setAmount(addExpenseDto.getAmount());

        expenseRepository.save(expense);
        String data = goalServiceImpl.addAmountToGoal(expense.getAmount(), goalId);
        Optional<Goal> goalOptional = goalRepository.findById(goalId);

        GoalExpenseResponseDto goalExpenseResponseDto = dtoMapper.toGoalExpenseDto(expense,goalOptional.get(),data);
        goalExpenseResponseDto.setOverLimit(createHashMap(expense));
        return goalExpenseResponseDto;

    }
    @Override
    public ExpenseResponseDto viewExpense(int expenseId) {
        User user = userServiceImpl.getCurrentUser();
        Optional<Expense> expenseOptional = expenseRepository.findById(expenseId);
        if(expenseOptional.isPresent() && expenseOptional.get().getUser().getId() == user.getId()){
            ExpenseResponseDto expenseResponseDto = dtoMapper.toExpenseDto(expenseOptional.get());
            expenseResponseDto.setOverLimit(createHashMap(expenseOptional.get()));
            return expenseResponseDto;
        }
        return null;
    }

    @Override
    public List<ExpenseResponseDto> viewExpenseList() {
        User user = userServiceImpl.getCurrentUser();
        List<Expense> expenses = expenseRepository.findAllByUserId(user.getId());
        List<ExpenseResponseDto> expenseResponseDtos = new ArrayList<>();
        for (Expense expense:
             expenses) {
            ExpenseResponseDto expenseResponseDto = dtoMapper.toExpenseDto(expense);
            expenseResponseDto.setOverLimit(createHashMap(expense));
            expenseResponseDtos.add(expenseResponseDto);
        }
        return expenseResponseDtos;
    }

    @Override
    public ExpenseResponseDto updateExpense(int expenseId, AddExpenseDto addExpenseDto) {
        User user = userServiceImpl.getCurrentUser();
        Optional<Expense> expenseOptional = expenseRepository.findById(expenseId);
        if(expenseOptional.isPresent() && user.getId() == expenseOptional.get().getUser().getId()){
            Expense expense = expenseOptional.get();
            expense.setUser(user);
            expense.setCategory(addExpenseDto.getCategory());
            expense.setDescription(addExpenseDto.getDescription());
            expense.setAmount(addExpenseDto.getAmount());
            expenseRepository.save(expense);

            ExpenseResponseDto expenseResponseDto = dtoMapper.toExpenseDto(expense);
            expenseResponseDto.setOverLimit(createHashMap(expense));
            return expenseResponseDto;
        }
        return null;
    }

    @Override
    public GoalExpenseResponseDto updateGoalExpense(int expenseId, AddGoalExpenseDto addGoalExpenseDto) {
        Optional<Expense> expenseOptional = expenseRepository.findById(expenseId);
        User user = userServiceImpl.getCurrentUser();
        Optional<Goal> goalOptional = goalRepository.findById(addGoalExpenseDto.getGoalId());
        if(expenseOptional.isPresent() && goalOptional.isPresent() && user.getId() == expenseOptional.get().getUser().getId()){

            BigDecimal oldAmount = expenseOptional.get().getAmount();
            BigDecimal newAmount = addGoalExpenseDto.getAmount();
            goalOptional.get().setAmountSaved(goalOptional.get().getAmountSaved().subtract(oldAmount).add(newAmount));
            if(goalOptional.get().getTotalAmount().compareTo(goalOptional.get().getAmountSaved())>0){
                goalOptional.get().setStatus("progress");
            }
            else {
                goalOptional.get().setStatus("achieved");
            }
            goalRepository.save(goalOptional.get());

            Expense expense = expenseOptional.get();
            expense.setUser(user);
            expense.setCategory(addGoalExpenseDto.getCategory());
            expense.setDescription(addGoalExpenseDto.getDescription());
            expense.setAmount(addGoalExpenseDto.getAmount());
            expenseRepository.save(expenseOptional.get());

            GoalExpenseResponseDto goalExpenseResponseDto = dtoMapper.toGoalExpenseDto(expense,goalOptional.get(),"");
            goalExpenseResponseDto.setOverLimit(createHashMap(expenseOptional.get()));
            return goalExpenseResponseDto;
        }
        return null;
    }

    @Override
    public String deleteExpense(int expenseId) {
        User user = userServiceImpl.getCurrentUser();
        Optional<Expense> expense = expenseRepository.findById(expenseId);
        if(expense.isPresent() && user.getId() == expense.get().getUser().getId()){
            if(expense.get().getCategory()!=ExpenseEnum.GOAL){
                expenseRepository.deleteById(expenseId);
                return "success";
            }
            else {
                return "goal id not given";
            }
        }
        return "user unauthorized";
    }

    @Override
    public String deleteGoalExpense(int goalId, int expenseId) {
        Optional<Expense> expense = expenseRepository.findById(expenseId);
        Optional<Goal> goal = goalRepository.findById(goalId);
        User user = userServiceImpl.getCurrentUser();
        if(expense.isPresent() && goal.isPresent() && user.getId() == expense.get().getUser().getId()){
            BigDecimal amountReduced = expense.get().getAmount();
            goal.get().setAmountSaved(goal.get().getAmountSaved().subtract(amountReduced));
            if(goal.get().getTotalAmount().compareTo(goal.get().getAmountSaved())>0){
                goal.get().setStatus("progress");
            }
            goalRepository.save(goal.get());
            expenseRepository.deleteById(expenseId);
            return "success";
        }
        return "expense with id: "+expenseId+" or goal with id: "+ goalId +" not present";
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
    public HashMap<RecurrenceEnum,Boolean> createHashMap(Expense expense){
        List<Boolean> checkLimitInInterval = this.checkLimitInInterval(expense);
        RecurrenceEnum[] interval = RecurrenceEnum.values();
        HashMap<RecurrenceEnum,Boolean> overLimit = new HashMap<>();
        for(int i=0; i<interval.length;i++){
            overLimit.put(interval[i], checkLimitInInterval.get(i));
        }
        return overLimit;
    }
}
