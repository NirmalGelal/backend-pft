package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.request.AddExpenseDto;
import com.nirmal.personalfinancetracker.dto.request.AddGoalExpenseDto;
import com.nirmal.personalfinancetracker.dto.request.GoalExpenseIdDto;
import com.nirmal.personalfinancetracker.dto.response.ExpenseResponseDto;
import com.nirmal.personalfinancetracker.dto.response.GoalExpenseResponseDto;
import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.service.impl.ExpenseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    @Autowired
    private ExpenseServiceImpl expenseServiceImpl;

    @GetMapping("/expense")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response<List<ExpenseResponseDto>>> listAllExpenses(){
        Response<List<ExpenseResponseDto>> response = new Response<>();
        List<ExpenseResponseDto> expenseResponseDtos = expenseServiceImpl.viewExpenseList();
        response.successResponse(expenseResponseDtos,"list retrieved successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/expense/{expenseId}")
    public ResponseEntity<Response<ExpenseResponseDto>> viewExpense (@PathVariable int expenseId){
        Response<ExpenseResponseDto> response = new Response<>();
        ExpenseResponseDto expenseResponseDto = expenseServiceImpl.viewExpense(expenseId);
        if(expenseResponseDto!=null){
            response.successResponse(expenseResponseDto,"expense retrieved successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.failureResponse("user unauthorized");
        return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
    }

    @PostMapping("/expense")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response<ExpenseResponseDto>> addExpense(@RequestBody AddExpenseDto addExpenseDto){
        Response<ExpenseResponseDto> response = new Response<>();
        ExpenseResponseDto expenseResponseDto = expenseServiceImpl.addExpense(addExpenseDto);
        response.successResponse(expenseResponseDto,"expense added");
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/expense/{expenseId}")
    public ResponseEntity<Response<ExpenseResponseDto>> editExpense(@PathVariable int expenseId, @RequestBody AddExpenseDto addExpenseDto){
        Response<ExpenseResponseDto> response = new Response<>();
        ExpenseResponseDto expenseResponseDto = expenseServiceImpl.updateExpense(expenseId,addExpenseDto);
        if(expenseResponseDto!=null){
            response.successResponse(expenseResponseDto,"expense updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("user not authorized");
        return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/expense/{expenseId}")
    public ResponseEntity<Response<String>> deleteExpense(@PathVariable int expenseId){
        Response<String> response = new Response<>();
        String data = expenseServiceImpl.deleteExpense(expenseId);
        if(data.equals("success")){
            response.successResponse(null,data);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.failureResponse(data);
        return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/goal-expense/{goalId}")
    public ResponseEntity<Response<GoalExpenseResponseDto>> addGoalExpense(@PathVariable int goalId,
                                                                           @RequestBody AddExpenseDto addExpenseDto){
        Response<GoalExpenseResponseDto> response = new Response<>();
        GoalExpenseResponseDto goalExpenseResponseDto = expenseServiceImpl.addExpense(goalId,addExpenseDto);
        response.successResponse(goalExpenseResponseDto,"goal expense added successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/goal-expense/{expenseId}")
    public ResponseEntity<Response<GoalExpenseResponseDto>> editGoalExpense(@PathVariable int expenseId, @RequestBody AddGoalExpenseDto addGoalExpense){
        Response<GoalExpenseResponseDto> response = new Response<>();
        GoalExpenseResponseDto goalExpenseResponseDto = expenseServiceImpl.updateGoalExpense(expenseId,addGoalExpense);
        if(goalExpenseResponseDto!=null){
            response.successResponse(goalExpenseResponseDto,"goal expense updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("user not authorized");
        return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/goal-expense")
    public ResponseEntity<Response<String>> deleteGoalExpense(@RequestBody GoalExpenseIdDto goalExpenseIdDto){
        Response<String> response = new Response<>();
        String data = expenseServiceImpl.deleteGoalExpense(goalExpenseIdDto.getGoalId(), goalExpenseIdDto.getExpenseId());
        if(data.equals("success")){
            response.successResponse(null,data);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.failureResponse(data);
        return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
    }

}
