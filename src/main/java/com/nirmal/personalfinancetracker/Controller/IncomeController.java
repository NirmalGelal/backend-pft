package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.request.AddIncomeDto;
import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.model.Income;
import com.nirmal.personalfinancetracker.service.impl.IncomeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IncomeController{

    @Autowired
    private IncomeServiceImpl incomeServiceImpl;

    @GetMapping("/incomes")
    public ResponseEntity<Response<List<Income>>> viewIncomeList(){
        Response<List<Income>> response = new Response<>();
        List<Income> incomes= incomeServiceImpl.viewIncomeList();
        if(!incomes.isEmpty()){
            response.setStatus(true);
            response.setData(incomes);
            response.setMessage("Income list retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setStatus(false);
        response.setMessage("Income database is empty");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/incomes/{id}")
    public ResponseEntity<Response<Income>> viewIncomeById(@PathVariable int id){
        Response<Income> response = new Response<>();
        Income income = incomeServiceImpl.viewIncomeById(id);
        if(income!=null){
            response.setMessage("income retrieved successfully");
            response.setStatus(true);
            response.setData(income);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setStatus(false);
        response.setMessage("invalid id");
        return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/income")
    public ResponseEntity<Response<Income>> addIncome(@RequestBody AddIncomeDto addIncomeDto){
        Response<Income> response = new Response<>();
        Income income = incomeServiceImpl.addIncome(addIncomeDto);
        response.setMessage("income added successfully");
        response.setStatus(true);
        response.setData(income);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/income/{id}")
    public ResponseEntity<Response<Income>> updateIncome(@PathVariable int id, @RequestBody AddIncomeDto addIncomeDto){
        Response<Income> response = new Response<>();
        Income income = incomeServiceImpl.updateIncome(id,addIncomeDto);
        if(income!=null){
            response.setData(income);
            response.setMessage("income updated successfully");
            response.setStatus(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setStatus(false);
        response.setMessage("invalid id");
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/income/{id}")
    public ResponseEntity<Response<String>> deleteIncome(@PathVariable int id){
        Response<String> response = new Response<>();
        String data = incomeServiceImpl.deleteIncome(id);
        if(data=="success"){
            response.setData(data);
            response.setMessage("income deleted successfully");
            response.setStatus(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setStatus(false);
        response.setMessage("invalid id");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
