package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.request.BudgetLimitRequestDto;
import com.nirmal.personalfinancetracker.dto.response.BudgetLimitResponseDto;
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
    public ResponseEntity<Response<List<BudgetLimitResponseDto>>> viewBudgetLimitList(){
        Response<List<BudgetLimitResponseDto>> response = new Response<>();
        List<BudgetLimitResponseDto> budgetLimitResponseDtos = budgetLimitServiceImpl.viewBudgetLimitList();
        if(budgetLimitResponseDtos!=null){
            response.successResponse(budgetLimitResponseDtos,"list retrieved successfully");
            return  new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("budget limit database is empty");
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @GetMapping("/budget-limits/{id}")
    public ResponseEntity<Response<BudgetLimitResponseDto>> viewBudgetLimitById(@PathVariable int id){
        Response<BudgetLimitResponseDto> response = new Response<>();
        BudgetLimitResponseDto budgetLimitResponseDto = budgetLimitServiceImpl.viewBudgetLimitById(id);
        if(budgetLimitResponseDto!=null){
            response.successResponse(budgetLimitResponseDto,"Budget Limit retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("Invalid id");
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @PostMapping("/budget-limits")
    public ResponseEntity<Response<BudgetLimitResponseDto>> addBudgetLimit(@RequestBody BudgetLimitRequestDto budgetLimitRequestDto){
        Response<BudgetLimitResponseDto> response = new Response<>();
        BudgetLimitResponseDto budgetLimitResponseDto = budgetLimitServiceImpl.addBudgetLimit(budgetLimitRequestDto);
        response.successResponse(budgetLimitResponseDto,"budget limit added successfully");
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PutMapping("/budget-limits/{id}")
    public ResponseEntity<Response<BudgetLimitResponseDto>> updateBudgetLimit(@PathVariable int id, @RequestBody BudgetLimitRequestDto budgetLimitRequestDto){
        Response<BudgetLimitResponseDto> response = new Response<>();
        BudgetLimitResponseDto budgetLimitResponseDto = budgetLimitServiceImpl.updateBudgetLimit(id, budgetLimitRequestDto);
        if(budgetLimitResponseDto!=null){
            response.successResponse(budgetLimitResponseDto,"budget limit updated successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.failureResponse("Invalid id");
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("budget-limits/{id}")
    public ResponseEntity<Response<String>> deleteBudgetLimit(@PathVariable int id){
        Response<String> response = new Response<>();
        String data = budgetLimitServiceImpl.deleteBudgetLimit(id);
        if(data.equals("success")){
            response.successResponse(data,"budget limit deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("Invalid id");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
