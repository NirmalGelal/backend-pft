package com.nirmal.personalfinancetracker.service.impl;

import com.nirmal.personalfinancetracker.dto.request.AddIncomeDto;
import com.nirmal.personalfinancetracker.dto.response.IncomeDto;
import com.nirmal.personalfinancetracker.model.Income;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.repository.IncomeRepository;
import com.nirmal.personalfinancetracker.repository.UserRepository;
import com.nirmal.personalfinancetracker.service.DtoMapper;
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
    private UserRepository userRepository;
    @Autowired
    private DtoMapperImpl dtoMapper;

    @Override
    public IncomeDto addIncome(AddIncomeDto addIncomeDto) {
        User user = userRepository.findById(addIncomeDto.getUserId()).get();
        Income income = new Income();
        income.setUser(user);
        income.setCategory(addIncomeDto.getCategory());
        income.setAmount(addIncomeDto.getAmount());
        income.setDescription(addIncomeDto.getDescription());
        incomeRepository.save(income);
        return dtoMapper.toIncomeDto(income);
    }

    @Override
    public List<IncomeDto> viewIncomeList() {
        List<Income> incomes = incomeRepository.findAll();
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
        if(incomeOptional.isPresent()){
            Income income = incomeOptional.get();
            User user = userRepository.findById(addIncomeDto.getUserId()).get();
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
        if(income.isPresent()){
            incomeRepository.deleteById(incomeId);
            return "success";
        }
        return "income with id: "+incomeId+ " not present.";
    }

}
