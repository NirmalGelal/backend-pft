package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.request.AddIncomeDto;
import com.nirmal.personalfinancetracker.dto.response.IncomeDto;
import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.service.impl.IncomeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IncomeController {

    @Autowired
    private IncomeServiceImpl incomeServiceImpl;

    @GetMapping("/incomes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response<List<IncomeDto>>> viewIncomeList() {
        Response<List<IncomeDto>> response = new Response<>();
        List<IncomeDto> incomeDtos = incomeServiceImpl.viewIncomeList(
                ((User) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal()
                ).getId());
        response.successResponse(incomeDtos, "Income list retrieved successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/incomes/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response<IncomeDto>> viewIncomeById(@PathVariable int id) {
        Response<IncomeDto> response = new Response<>();
        if(id == ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
            IncomeDto incomeDto = incomeServiceImpl.viewIncomeById(id);
            if (incomeDto != null) {
                response.successResponse(incomeDto, "Income retrieved successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        response.failureResponse("User unauthorized");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @PostMapping("/income")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Response<IncomeDto>> addIncome(@RequestBody AddIncomeDto addIncomeDto) {
        Response<IncomeDto> response = new Response<>();
        IncomeDto incomeDto = incomeServiceImpl.addIncome(addIncomeDto);
        response.successResponse(incomeDto, "income added successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/income/{id}")
    public ResponseEntity<Response<IncomeDto>> updateIncome(@PathVariable int incomeId, @RequestBody AddIncomeDto addIncomeDto) {
        Response<IncomeDto> response = new Response<>();
        IncomeDto incomeDto = incomeServiceImpl.updateIncome(incomeId,addIncomeDto);
        if (incomeDto != null) {
            response.successResponse(incomeDto, "income updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("invalid id");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/income/{id}")
    public ResponseEntity<Response<String>> deleteIncome(@PathVariable int id) {
        Response<String> response = new Response<>();
        String data = incomeServiceImpl.deleteIncome(id);
        if (data.equals("success")) {
            response.successResponse(data, "income deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("invalid id");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
