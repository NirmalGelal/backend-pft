package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.request.BudgetLimitRequestDto;
import com.nirmal.personalfinancetracker.dto.response.BudgetLimitResponseDto;
import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.service.impl.BudgetLimitServiceImpl;
import com.nirmal.personalfinancetracker.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BudgetLimitController {
    @Autowired
    private BudgetLimitServiceImpl budgetLimitServiceImpl;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @GetMapping("/limit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response<List<BudgetLimitResponseDto>>> viewBudgetLimitList(){
        Response<List<BudgetLimitResponseDto>> response = new Response<>();
        User user = userServiceImpl.getCurrentUser();
        List<BudgetLimitResponseDto> budgetLimitResponseDtos = budgetLimitServiceImpl.viewBudgetLimitList();
        response.successResponse(budgetLimitResponseDtos,"list retrieved successfully");
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/limit/{budgetLimitId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response<BudgetLimitResponseDto>> viewBudgetLimitById(@PathVariable int budgetLimitId){
        Response<BudgetLimitResponseDto> response = new Response<>();
        BudgetLimitResponseDto budgetLimitResponseDto = budgetLimitServiceImpl.viewBudgetLimitById(budgetLimitId);
        if(budgetLimitResponseDto!=null){
            response.successResponse(budgetLimitResponseDto,"Budget Limit retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("user unauthorized");
        return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
    }

    @PostMapping("/limit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response<BudgetLimitResponseDto>> addBudgetLimit(@RequestBody BudgetLimitRequestDto budgetLimitRequestDto){
        Response<BudgetLimitResponseDto> response = new Response<>();
        BudgetLimitResponseDto budgetLimitResponseDto = budgetLimitServiceImpl.addBudgetLimit(budgetLimitRequestDto);
        response.successResponse(budgetLimitResponseDto,"budget limit added successfully");
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/limit/{budgetLimitId}")
    public ResponseEntity<Response<BudgetLimitResponseDto>> updateBudgetLimit(@PathVariable int budgetLimitId, @RequestBody BudgetLimitRequestDto budgetLimitRequestDto){
        Response<BudgetLimitResponseDto> response = new Response<>();
        BudgetLimitResponseDto budgetLimitResponseDto = budgetLimitServiceImpl.updateBudgetLimit(budgetLimitId, budgetLimitRequestDto);
        if(budgetLimitResponseDto!=null){
            response.successResponse(budgetLimitResponseDto,"budget limit updated successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.failureResponse("user not authorized");
        return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("limit/{id}")
    public ResponseEntity<Response<String>> deleteBudgetLimit(@PathVariable int id){
        Response<String> response = new Response<>();
        String data = budgetLimitServiceImpl.deleteBudgetLimit(id);
        if(data.equals("success")){
            response.successResponse(data,"budget limit deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setData(data);
        response.setMessage("user not authorized");
        response.setStatus(false);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
