package com.nirmal.personalfinancetracker.service.impl;


import com.nirmal.personalfinancetracker.dto.request.AddGoalDto;
import com.nirmal.personalfinancetracker.model.Goal;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.repository.GoalRepository;
import com.nirmal.personalfinancetracker.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;

    @Override
    public Goal addGoal(AddGoalDto addGoalDto) {
        Goal goal = new Goal();
        User user = userRepository.findById(addGoalDto.getUserId()).get();
        goal.setUser(user);
        goal.setName(addGoalDto.getName());
        goal.setStatus("progress");
        goal.setTotalAmount(addGoalDto.getTotalAmount());
        goalRepository.save(goal);
        return goal;
    }

    @Override
    public List<Goal> viewGoalList() {
        return goalRepository.findAll();
    }

    @Override
    public Goal viewGoalById(int goalId) {
        Optional<Goal> goal = goalRepository.findById(goalId);
        if(goal.isPresent()){
            return goal.get();
        }
        return null;
    }

    @Override
    public Goal updateGoal(int goalId, AddGoalDto addGoalDto) {
        Optional<Goal> goalOptional = goalRepository.findById(goalId);
        if(goalOptional.isPresent()){
            Goal goal = new Goal();
            goal.setId(goalOptional.get().getId());
            goal.setUser(userRepository.findById(addGoalDto.getUserId()).get());
            goal.setName(addGoalDto.getName());
            goal.setTotalAmount(addGoalDto.getTotalAmount());
            goalRepository.save(goal);

            return goal;
        }
        return null;
    }

    @Override
    public String deleteGoal(int goalId) {
        Optional<Goal> goal = goalRepository.findById(goalId);
        if(goal.isPresent()){
            goalRepository.deleteById(goalId);
            return "success";
        }
        return "goal with id: "+goalId+ " not present.";
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
