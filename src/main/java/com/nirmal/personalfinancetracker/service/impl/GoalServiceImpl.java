package com.nirmal.personalfinancetracker.service.impl;


import com.nirmal.personalfinancetracker.dto.request.AddGoalDto;
import com.nirmal.personalfinancetracker.dto.request.AddIncomeDto;
import com.nirmal.personalfinancetracker.dto.response.GoalDto;
import com.nirmal.personalfinancetracker.enums.IncomeEnum;
import com.nirmal.personalfinancetracker.model.Goal;
import com.nirmal.personalfinancetracker.model.Income;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.repository.GoalRepository;
import com.nirmal.personalfinancetracker.repository.IncomeRepository;
import com.nirmal.personalfinancetracker.repository.UserRepository;
import com.nirmal.personalfinancetracker.service.DtoMapper;
import com.nirmal.personalfinancetracker.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GoalServiceImpl implements GoalService {

    @Autowired
    private GoalRepository goalRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private IncomeServiceImpl incomeService;
    @Autowired
    private DtoMapper dtoMapper;

    @Override
    public GoalDto addGoal(AddGoalDto addGoalDto) {
        Goal goal = new Goal();
        User user = userServiceImpl.getCurrentUser();
        goal.setUser(user);
        goal.setName(addGoalDto.getName());
        goal.setStatus("progress");
        goal.setTotalAmount(addGoalDto.getTotalAmount());
        goalRepository.save(goal);
        return dtoMapper.toGoalDto(goal);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public List<GoalDto> viewGoalList() {
        User user = userServiceImpl.getCurrentUser();
        List<Goal> goals = goalRepository.findAllByUserId(user.getId());
        List<GoalDto> goalDtos = new ArrayList<>();
        for (Goal goal:
             goals) {
            goalDtos.add(dtoMapper.toGoalDto(goal));
        }
        return goalDtos;
    }

    @Override
    public GoalDto viewGoalById(int goalId) {
        Optional<Goal> goal = goalRepository.findById(goalId);
        User user = userServiceImpl.getCurrentUser();
        if(goal.isPresent() && goal.get().getUser().getId() == user.getId()){
            return dtoMapper.toGoalDto(goal.get());
        }
        return null;
    }

    @Override
    public GoalDto updateGoal(int goalId, AddGoalDto addGoalDto) {
        Optional<Goal> goalOptional = goalRepository.findById(goalId);
        User user = userServiceImpl.getCurrentUser();
        if(goalOptional.isPresent() && goalOptional.get().getUser().getId() == user.getId()){
            Goal goal = new Goal();
            goal.setId(goalOptional.get().getId());
            goal.setUser(user);
            goal.setName(addGoalDto.getName());
            goal.setTotalAmount(addGoalDto.getTotalAmount());
            goal.setAmountSaved(goalOptional.get().getAmountSaved());
            if(addGoalDto.getTotalAmount().compareTo(goalOptional.get().getAmountSaved())>0){
                goal.setStatus("progress");
            }
            else {
                goal.setStatus("achieved");
            }
            goalRepository.save(goal);

            return dtoMapper.toGoalDto(goal);
        }
        return null;
    }

    @Override
    public String deleteGoal(int goalId) {
        Optional<Goal> goal = goalRepository.findById(goalId);
        User user = userServiceImpl.getCurrentUser();
        if(goal.isPresent() && goal.get().getUser().getId() == user.getId()){
            if(goal.get().getAmountSaved().compareTo(BigDecimal.ZERO) == 0){
                goalRepository.deleteById(goalId);
                return "success";
            }
            BigDecimal amount = goal.get().getAmountSaved();

            AddIncomeDto addIncomeDto = new AddIncomeDto();
            addIncomeDto.setUserId(user.getId());
            addIncomeDto.setAmount(amount);
            addIncomeDto.setDescription("income from the saved goal: "+goal.get().getName());
            addIncomeDto.setCategory(IncomeEnum.OTHER);

            incomeService.addIncome(addIncomeDto);
            goalRepository.deleteById(goalId);
            return "success";

        }
        return "unauthorized user";
    }

    @Override
    public String addAmountToGoal(BigDecimal amount, int goalId) {
        Optional<Goal> goalOptional = goalRepository.findById(goalId);
        Goal goal = goalOptional.get();
        BigDecimal newAmount = goal.getAmountSaved().add(amount);
        goal.setAmountSaved(newAmount);
        if(newAmount.compareTo(goal.getTotalAmount())>=0){
            goal.setStatus("achieved");
            goalRepository.save(goal);
            return "amount added successfully, Goal is achieved";
        }
        goalRepository.save(goal);
        return "amount added successfully, amount left: "+(goal.getTotalAmount().subtract(goal.getAmountSaved()));
    }
}
