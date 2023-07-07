package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.request.AddExpenseDto;
import com.nirmal.personalfinancetracker.dto.response.ExpenseResponseDto;
import com.nirmal.personalfinancetracker.dto.response.GoalExpenseResponseDto;
import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.enums.ExpenseEnum;
import com.nirmal.personalfinancetracker.enums.RecurrenceEnum;
import com.nirmal.personalfinancetracker.service.impl.ExpenseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Response<List<ExpenseResponseDto>>> listAllExpenses(){
        Response<List<ExpenseResponseDto>> response = new Response<>();
        List<ExpenseResponseDto> expenseResponseDtos = expenseServiceImpl.viewExpenseList();
        if(!expenseResponseDtos.isEmpty()){
            response.successResponse(expenseResponseDtos,"list retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("database is empty");
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(204));
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<Response<ExpenseResponseDto>> viewExpense (@PathVariable int id){
        Response<ExpenseResponseDto> response = new Response<>();
        ExpenseResponseDto expenseResponseDto = expenseServiceImpl.viewExpense(id);
        response.successResponse(expenseResponseDto,"expense retrieved successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @PostMapping("/expense")
    public ResponseEntity<Response<ExpenseResponseDto>> addExpense(@RequestBody AddExpenseDto addExpenseDto){
        Response<ExpenseResponseDto> response = new Response<>();
        ExpenseResponseDto expenseResponseDto = expenseServiceImpl.addExpense(addExpenseDto);
        response.successResponse(expenseResponseDto,"expense added");
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PostMapping("/goal-expense/{goalId}")
    public ResponseEntity<Response<GoalExpenseResponseDto>> addGoalExpense(@PathVariable int goalId,
                                                                           @RequestBody AddExpenseDto addExpenseDto){
        Response<GoalExpenseResponseDto> response = new Response<>();
        GoalExpenseResponseDto goalExpenseResponseDto = expenseServiceImpl.addExpense(goalId,addExpenseDto);
        response.successResponse(goalExpenseResponseDto,"goal expense added successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/total-expense")
    public ResponseEntity<Response<BigDecimal>> calculateTotalExpenseAtInterval(@RequestParam ExpenseEnum category,
                                                                                @RequestParam RecurrenceEnum interval){
        Response<BigDecimal> response = new Response<>();
        BigDecimal totalAmount = expenseServiceImpl.totalExpenseInInterval(category, interval);
        response.successResponse(totalAmount,"total expense for "+ interval + " successfully calculated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/expense/{id}")
    public ResponseEntity<Response<ExpenseResponseDto>> editExpense(@PathVariable int id,@RequestBody AddExpenseDto addExpenseDto){
        Response<ExpenseResponseDto> response = new Response<>();
        ExpenseResponseDto expenseResponseDto = expenseServiceImpl.updateExpense(id,addExpenseDto);
        response.successResponse(expenseResponseDto,"expense updated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/expense/{id}")
    public ResponseEntity<Response<String>> deleteExpense(@PathVariable int id){
        Response<String> response = new Response<>();
        String data = expenseServiceImpl.deleteExpense(id);
        if(data.equals("success")){
            response.successResponse(null,data);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.failureResponse(data);
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/goal-expense/{expenseId}")
    public ResponseEntity<Response<String>> deleteGoalExpense(@PathVariable int expenseId, @RequestParam int goalId){
        Response<String> response = new Response<>();
        String data = expenseServiceImpl.deleteGoalExpense(goalId,expenseId);
        if(data.equals("success")){
            response.successResponse(null,data);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.failureResponse(data);
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
