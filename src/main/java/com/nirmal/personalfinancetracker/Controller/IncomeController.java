package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.request.AddIncomeDto;
import com.nirmal.personalfinancetracker.dto.response.IncomeDto;
import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.service.impl.IncomeServiceImpl;
import com.nirmal.personalfinancetracker.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IncomeController {

    @Autowired
    private IncomeServiceImpl incomeServiceImpl;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/income")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response<List<IncomeDto>>> viewIncomeList() {
        Response<List<IncomeDto>> response = new Response<>();
        User user = userServiceImpl.getCurrentUser();
        List<IncomeDto> incomeDtos = incomeServiceImpl.viewIncomeList(user.getId());
        response.successResponse(incomeDtos, "Income list retrieved successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/income/{incomeId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response<IncomeDto>> viewIncomeById(@PathVariable int incomeId) {
        Response<IncomeDto> response = new Response<>();
        User user = userServiceImpl.getCurrentUser();
        IncomeDto incomeDto = incomeServiceImpl.viewIncomeById(incomeId);
        if (incomeDto != null) {
            if (incomeDto.getUserId() == user.getId()){
                response.successResponse(incomeDto, "Income retrieved successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.failureResponse("User unauthorized");
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
        response.failureResponse("invalid id");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/income")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response<IncomeDto>> addIncome(@RequestBody AddIncomeDto addIncomeDto) {
        Response<IncomeDto> response = new Response<>();
        IncomeDto incomeDto = incomeServiceImpl.addIncome(addIncomeDto);
        response.successResponse(incomeDto, "income added successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/income/{incomeId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response<IncomeDto>> updateIncome(@PathVariable int incomeId, @RequestBody AddIncomeDto addIncomeDto) {
        Response<IncomeDto> response = new Response<>();
        IncomeDto incomeDto = incomeServiceImpl.updateIncome(incomeId,addIncomeDto);
        if (incomeDto != null) {
            response.successResponse(incomeDto, "income updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("user not authorized");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/income/{incomeId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response<String>> deleteIncome(@PathVariable int incomeId) {
        Response<String> response = new Response<>();
        String data = incomeServiceImpl.deleteIncome(incomeId);
        if (data.equals("success")) {
            response.successResponse(data, "income deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else if (data.equals("user not authorized")) {
            response.failureResponse("user not authorized");
            return new ResponseEntity<>(response,HttpStatus.FORBIDDEN);
        } else {
            response.failureResponse("invalid id");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
