package com.nirmal.personalfinancetracker.service;

import com.nirmal.personalfinancetracker.model.Income;

import java.util.List;

public interface IncomeService {

    public Income addIncome(Income income);
    public List<Income> viewIncomeList();
    public Income updateIncome(Income income);
    public String deleteIncome(int incomeId);
}
