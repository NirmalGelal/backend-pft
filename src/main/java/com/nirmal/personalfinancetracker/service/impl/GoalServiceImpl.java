package com.nirmal.personalfinancetracker.service.impl;


import com.nirmal.personalfinancetracker.model.Goal;
import com.nirmal.personalfinancetracker.repository.GoalRepository;
import com.nirmal.personalfinancetracker.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class GoalServiceImpl implements GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Override
    public Goal addGoal(Goal goal) {
        goalRepository.save(goal);
        return goal;
    }

    @Override
    public List<Goal> viewGoalList() {
        return goalRepository.findAll();
    }

    @Override
    public Goal updateGoal(Goal goal) {
        goalRepository.save(goal);
        return goal;
    }

    @Override
    public String deleteGoal(int goalId) {
        Optional<Goal> goal = goalRepository.findById(goalId);
        if(goal.isPresent()){
            goalRepository.deleteById(goalId);
            return "goal deleted successfully.";
        }
        return "goal with id: "+goal.get().getId()+ " not present.";
    }

    @Override
    public String addAmountToGoal(BigDecimal amount, int goalId) {
        Optional<Goal> goalOptional = goalRepository.findById(goalId);
        Goal goal = goalOptional.get();
        BigDecimal newAmount = goal.getAmountSaved().add(amount);
        goal.setAmountSaved(newAmount);
        goalRepository.save(goal);
        return "amount added successfully, amount left: "+(goal.getTotalAmount().subtract(goal.getAmountSaved()));
    }
}
