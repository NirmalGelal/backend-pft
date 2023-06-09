package com.nirmal.personalfinancetracker.service.impl;

import com.nirmal.personalfinancetracker.model.Income;
import com.nirmal.personalfinancetracker.repository.IncomeRepository;
import com.nirmal.personalfinancetracker.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeServiceImpl implements IncomeService {
    @Autowired
    private IncomeRepository incomeRepository;


    @Override
    public Income addIncome(Income income) {
        incomeRepository.save(income);
        return income;
    }

    @Override
    public List<Income> viewIncomeList() {
        return incomeRepository.findAll();
    }

    @Override
    public Income updateIncome(Income income) {
        incomeRepository.save(income);
        return income;
    }

    @Override
    public String deleteIncome(int incomeId) {
        Optional<Income> income = incomeRepository.findById(incomeId);
        if(income.isPresent()){
            incomeRepository.deleteById(incomeId);
            return "income deleted successfully.";
        }
        return "income with id: "+income.get().getId()+ " not present.";
    }

}
