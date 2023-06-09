package com.nirmal.personalfinancetracker.service;

import com.nirmal.personalfinancetracker.model.Expense;
import com.nirmal.personalfinancetracker.model.Goal;

import java.math.BigDecimal;
import java.util.List;

public interface GoalService {

    public Goal addGoal(Goal goal);
    public List<Goal> viewGoalList();
    public Goal updateGoal(Goal goal);
    public String deleteGoal(int goalId);
    public String addAmountToGoal(BigDecimal amount, int goalId);

}
