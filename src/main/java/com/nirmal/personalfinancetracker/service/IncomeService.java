package com.nirmal.personalfinancetracker.service;

import com.nirmal.personalfinancetracker.dto.request.AddIncomeDto;
import com.nirmal.personalfinancetracker.model.Income;

import java.util.List;

public interface IncomeService {

    public Income addIncome(AddIncomeDto addIncomeDto);
    public List<Income> viewIncomeList();
    public Income viewIncomeById(int id);
    public Income updateIncome(int id, AddIncomeDto addIncomeDto);
    public String deleteIncome(int incomeId);
}
