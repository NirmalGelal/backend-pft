package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.enums.ExpenseEnum;
import com.nirmal.personalfinancetracker.enums.RecurrenceEnum;
import com.nirmal.personalfinancetracker.service.impl.BudgetLimitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    private BudgetLimitServiceImpl budgetLimitService;

    @GetMapping("/test")
    public String test(){

        return ""+budgetLimitService.getLimit(ExpenseEnum.TRANSPORTATION, RecurrenceEnum.WEEKLY);
    }
}
