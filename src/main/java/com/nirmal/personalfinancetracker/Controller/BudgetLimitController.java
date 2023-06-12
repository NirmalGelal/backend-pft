package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.request.BudgetLimitDto;
import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.model.BudgetLimit;
import com.nirmal.personalfinancetracker.service.impl.BudgetLimitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BudgetLimitController {
    @Autowired
    private BudgetLimitServiceImpl budgetLimitServiceImpl;
    @GetMapping("/budget-limits")
    public ResponseEntity<Response<List<BudgetLimit>>> viewBudgetLimitList(){
        Response<List<BudgetLimit>> response = new Response<>();
        List<BudgetLimit> budgetLimitList = budgetLimitServiceImpl.viewBudgetLimitList();
        if(budgetLimitList!=null){
            response.setData(budgetLimitList);
            response.setMessage("list retrieved successfully");
            response.setStatus(true);
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setStatus(false);
        response.setMessage("budget limit database is empty");
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @GetMapping("/budget-limits/{id}")
    public ResponseEntity<Response<BudgetLimit>> viewBudgetLimitById(@PathVariable int id){
        Response<BudgetLimit> response = new Response<>();
        BudgetLimit budgetLimit = budgetLimitServiceImpl.viewBudgetLimitById(id);
        if(budgetLimit!=null){
            response.setData(budgetLimit);
            response.setStatus(true);
            response.setMessage("Budget Limit retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setMessage("Invalid id");
        response.setStatus(false);
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @PostMapping("/budget-limits")
    public ResponseEntity<Response<BudgetLimit>> addBudgetLimit(@RequestBody BudgetLimitDto budgetLimitDto){
        Response<BudgetLimit> response = new Response<>();
        BudgetLimit budgetLimit = budgetLimitServiceImpl.addBudgetLimit(budgetLimitDto);
        response.setStatus(true);
        response.setData(budgetLimit);
        response.setMessage("budget limit added successfully");
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/budget-limits/{id}")
    public ResponseEntity<Response<BudgetLimit>> updateBudgetLimit(@PathVariable int id, @RequestBody BudgetLimitDto budgetLimitDto){
        Response<BudgetLimit> response = new Response<>();
        BudgetLimit budgetLimit = budgetLimitServiceImpl.updateBudgetLimit(id, budgetLimitDto);
        if(budgetLimit!=null){
            response.setMessage("budget limit updated successfully");
            response.setStatus(true);
            response.setData(budgetLimit);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.setStatus(false);
        response.setMessage("Invalid id");
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("budget-limits/{id}")
    public ResponseEntity<Response<String>> deleteBudgetLimit(@PathVariable int id){
        Response<String> response = new Response<>();
        String data = budgetLimitServiceImpl.deleteBudgetLimit(id);
        if(data=="success"){
            response.setMessage("budget limit deleted successfully");
            response.setStatus(true);
            response.setData(data);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setMessage("Invalid id");
        response.setStatus(false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
