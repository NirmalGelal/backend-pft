package com.nirmal.personalfinancetracker.service.impl;

import com.nirmal.personalfinancetracker.repository.IncomeRepository;
import com.nirmal.personalfinancetracker.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeServiceImpl implements IncomeService {
    @Autowired
    private IncomeRepository incomeRepository;


}
