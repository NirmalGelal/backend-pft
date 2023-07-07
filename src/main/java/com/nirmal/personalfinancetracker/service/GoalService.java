package com.nirmal.personalfinancetracker.service;

import com.nirmal.personalfinancetracker.dto.request.AddGoalDto;
import com.nirmal.personalfinancetracker.dto.response.GoalDto;
import com.nirmal.personalfinancetracker.model.Expense;
import com.nirmal.personalfinancetracker.model.Goal;

import java.math.BigDecimal;
import java.util.List;

public interface GoalService {

    public GoalDto addGoal(AddGoalDto addGoalDto);
    public List<GoalDto> viewGoalList();
    public GoalDto viewGoalById(int goalId);
    public GoalDto updateGoal(int goalId, AddGoalDto addGoalDto);
    public String deleteGoal(int goalId);
    public String addAmountToGoal(BigDecimal amount, int goalId);

}
