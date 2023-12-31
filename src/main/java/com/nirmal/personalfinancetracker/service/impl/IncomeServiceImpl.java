package com.nirmal.personalfinancetracker.service.impl;

import com.nirmal.personalfinancetracker.dto.request.AddIncomeDto;
import com.nirmal.personalfinancetracker.dto.response.IncomeDto;
import com.nirmal.personalfinancetracker.model.Income;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.repository.IncomeRepository;
import com.nirmal.personalfinancetracker.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeServiceImpl implements IncomeService {
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    private DtoMapperImpl dtoMapper;

    @Override
    public IncomeDto addIncome(AddIncomeDto addIncomeDto) {
        User user = userServiceImpl.getCurrentUser();
        addIncomeDto.setUserId(user.getId());
        Income income = new Income();
        income.setUser(user);
        income.setCategory(addIncomeDto.getCategory());
        income.setAmount(addIncomeDto.getAmount());
        income.setDescription(addIncomeDto.getDescription());
        incomeRepository.save(income);
        return dtoMapper.toIncomeDto(income);
    }

    @Override
    public List<IncomeDto> viewIncomeList(int userId) {
        List<Income> incomes = incomeRepository.findAllByUserId(userId);
        List<IncomeDto> incomeDtos = new ArrayList<>();
        for (Income income:
             incomes) {
            incomeDtos.add(dtoMapper.toIncomeDto(income));
        }

        return incomeDtos;
    }

    @Override
    public IncomeDto viewIncomeById(int id) {
        Optional<Income> incomeOptional =  incomeRepository.findById(id);
        if(incomeOptional.isPresent()){
            Income income = incomeOptional.get();
            return dtoMapper.toIncomeDto(income);
        }
        return null;
    }

    @Override
    public IncomeDto updateIncome(int incomeId, AddIncomeDto addIncomeDto) {
        Optional<Income> incomeOptional = incomeRepository.findById(incomeId);
        User user = userServiceImpl.getCurrentUser();
        if(incomeOptional.isPresent() && user.getId() == incomeOptional.get().getUser().getId()){
            Income income = incomeOptional.get();
            addIncomeDto.setUserId(user.getId());

            income.setId(incomeId);
            income.setUser(user);
            income.setCategory(addIncomeDto.getCategory());
            income.setAmount(addIncomeDto.getAmount());
            income.setDescription(addIncomeDto.getDescription());

            incomeRepository.save(income);
            return dtoMapper.toIncomeDto(income);
        }
        return  null;
    }

    @Override
    public String deleteIncome(int incomeId) {
        Optional<Income> income = incomeRepository.findById(incomeId);
        User user = userServiceImpl.getCurrentUser();
        if(income.isPresent()){
            if(income.get().getUser().getId() == user.getId()){
                incomeRepository.deleteById(incomeId);
                return "success";
            }
            return "user not authorized";
        }
        return "income with id: "+incomeId+ " not present.";
    }

}
