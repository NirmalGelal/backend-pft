package com.nirmal.personalfinancetracker.service;

import com.nirmal.personalfinancetracker.dto.request.AddIncomeDto;
import com.nirmal.personalfinancetracker.dto.response.IncomeDto;
import com.nirmal.personalfinancetracker.model.Income;

import java.util.List;

public interface IncomeService {

    public IncomeDto addIncome(AddIncomeDto addIncomeDto);
    public List<IncomeDto> viewIncomeList(int id);
    public IncomeDto viewIncomeById(int id);
    public IncomeDto updateIncome(int incomeId,AddIncomeDto addIncomeDto);
    public String deleteIncome(int incomeId);
}
