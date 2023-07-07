package com.nirmal.personalfinancetracker.service;

import com.nirmal.personalfinancetracker.dto.request.AddIncomeDto;
import com.nirmal.personalfinancetracker.dto.response.IncomeDto;
import com.nirmal.personalfinancetracker.model.Income;

import java.util.List;

public interface IncomeService {

    public IncomeDto addIncome(AddIncomeDto addIncomeDto);
    public List<IncomeDto> viewIncomeList();
    public IncomeDto viewIncomeById(int id);
    public IncomeDto updateIncome(int id, AddIncomeDto addIncomeDto);
    public String deleteIncome(int incomeId);
}
