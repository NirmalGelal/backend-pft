package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.request.AddExpenseDto;
import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.enums.ExpenseEnum;
import com.nirmal.personalfinancetracker.enums.RecurrenceEnum;
import com.nirmal.personalfinancetracker.model.Expense;
import com.nirmal.personalfinancetracker.service.impl.ExpenseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    @Autowired
    private ExpenseServiceImpl expenseServiceImpl;

    @GetMapping("/expenses")
    public ResponseEntity<Response<List<Expense>>> listAllExpenses(){
        Response<List<Expense>> response = new Response<>();
        List<Expense> expenses = expenseServiceImpl.viewExpenseList();
        if(!expenses.isEmpty()){
            response.setData(expenses);
            response.setMessage("list retrieved successfully");
            response.setStatus(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setStatus(false);
        response.setData(null);
        response.setMessage("database is empty");
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(204));
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<Response<Expense>> viewExpense (@PathVariable int id){
        Response<Expense> response = new Response<>();
        Expense expense = expenseServiceImpl.viewExpense(id);
        response.setData(expense);
        response.setMessage("expense retrieved successfully");
        response.setStatus(true);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @PostMapping("/expense")
    public ResponseEntity<Response<Pair<Expense,List<Boolean>>>> addExpense(@RequestBody AddExpenseDto addExpenseDto){
        Response<Pair<Expense,List<Boolean>>> response = new Response<>();
        Pair<Expense,List<Boolean>> expense1 = expenseServiceImpl.addExpense(addExpenseDto);
        response.setMessage("expense added");
        response.setData(expense1);
        response.setStatus(true);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("/total-expense")
    public ResponseEntity<Response<BigDecimal>> calculateTotalExpenseAtInterval(@RequestParam ExpenseEnum category,
                                                                                @RequestParam RecurrenceEnum interval){
        Response<BigDecimal> response = new Response<>();
        BigDecimal totalAmount = expenseServiceImpl.totalExpenseInInterval(category, interval);
        response.setData(totalAmount);
        response.setStatus(true);
        response.setMessage("total expense for "+ interval + " successfully calculated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/expense/{id}")
    public ResponseEntity<Response<Pair<Expense,List<Boolean>>>> editExpense(@PathVariable int id,@RequestBody AddExpenseDto addExpenseDto){
        Response<Pair<Expense, List<Boolean>>> response = new Response<>();
        Pair<Expense, List<Boolean>> expenseListPair = expenseServiceImpl.updateExpense(id,addExpenseDto);
        response.setMessage("expense updated successfully");
        response.setStatus(true);
        response.setData(expenseListPair);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/expense/{id}")
    public ResponseEntity<Response<String>> deleteExpense(@PathVariable int id){
        Response<String> response = new Response<>();
        String data = expenseServiceImpl.deleteExpense(id);
        if(data.equals("success")){
            response.setStatus(true);
            response.setData(data);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.setStatus(false);
        response.setData(data);
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);

    }
}
