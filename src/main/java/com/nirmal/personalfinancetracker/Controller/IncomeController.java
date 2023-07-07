package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.request.AddIncomeDto;
import com.nirmal.personalfinancetracker.dto.response.IncomeDto;
import com.nirmal.personalfinancetracker.dto.response.Response;
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
    public ResponseEntity<Response<List<IncomeDto>>> viewIncomeList(){
        Response<List<IncomeDto>> response = new Response<>();
        List<IncomeDto> incomeDtos= incomeServiceImpl.viewIncomeList();
        if(!incomeDtos.isEmpty()){
            response.successResponse(incomeDtos,"Income list retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("Income database is empty");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/incomes/{id}")
    public ResponseEntity<Response<IncomeDto>> viewIncomeById(@PathVariable int id){
        Response<IncomeDto> response = new Response<>();
        IncomeDto incomeDto = incomeServiceImpl.viewIncomeById(id);
        if(incomeDto!=null){
            response.successResponse(incomeDto,"incomeDto retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("invalid id");
        return  new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/income")
    public ResponseEntity<Response<IncomeDto>> addIncome(@RequestBody AddIncomeDto addIncomeDto){
        Response<IncomeDto> response = new Response<>();
        IncomeDto incomeDto = incomeServiceImpl.addIncome(addIncomeDto);
        response.successResponse(incomeDto,"income added successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/income/{id}")
    public ResponseEntity<Response<IncomeDto>> updateIncome(@PathVariable int id, @RequestBody AddIncomeDto addIncomeDto){
        Response<IncomeDto> response = new Response<>();
        IncomeDto incomeDto = incomeServiceImpl.updateIncome(id,addIncomeDto);
        if(incomeDto!=null){
            response.successResponse(incomeDto,"income updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("invalid id");
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/income/{id}")
    public ResponseEntity<Response<String>> deleteIncome(@PathVariable int id){
        Response<String> response = new Response<>();
        String data = incomeServiceImpl.deleteIncome(id);
        if(data.equals("success")){
            response.successResponse(data,"income deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("invalid id");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
