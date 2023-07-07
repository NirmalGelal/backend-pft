package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.request.RecurringBillsRequestDto;
import com.nirmal.personalfinancetracker.dto.response.RecurringBillsResponseDto;
import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.model.RecurringBills;
import com.nirmal.personalfinancetracker.service.impl.RecurringBillsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecurringBillsController {
    @Autowired
    private RecurringBillsServiceImpl recurringBillsServiceImpl;
    @GetMapping("/recurring-bills")
    public ResponseEntity<Response<List<RecurringBillsResponseDto>>> viewRecurringBillsList(){
        Response<List<RecurringBillsResponseDto>> response = new Response<>();
        List<RecurringBillsResponseDto> recurringBillsResponseDtos = recurringBillsServiceImpl.viewRecurringBills();
        if(!recurringBillsResponseDtos.isEmpty()){
            response.successResponse(recurringBillsResponseDtos,"list retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("no recurringBillsResponseDtos in database");
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @GetMapping("/recurring-bills/{id}")
    public ResponseEntity<Response<RecurringBillsResponseDto>> viewRecurringBillsById(@PathVariable int id){
        Response<RecurringBillsResponseDto> response = new Response<>();
        RecurringBillsResponseDto recurringBillsResponseDto =  recurringBillsServiceImpl.recurringBillsById(id);
        if (recurringBillsResponseDto!=null){
            response.successResponse(recurringBillsResponseDto,"bill retrieved successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.failureResponse("invalid id");
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @PostMapping("/recurring-bills")
    public ResponseEntity<Response<RecurringBillsResponseDto>> addRecurringBills(@RequestBody RecurringBillsRequestDto recurringBillsRequestDto){
        Response<RecurringBillsResponseDto> response = new Response<>();
        RecurringBillsResponseDto recurringBillsResponseDto = recurringBillsServiceImpl.addRecurringBills(recurringBillsRequestDto);
        response.successResponse(recurringBillsResponseDto,"recurring bill added");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/recurring-bills/{id}")
    public ResponseEntity<Response<RecurringBillsResponseDto>> updateRecurringBills(@PathVariable int id,
                                                                         @RequestBody RecurringBillsRequestDto recurringBillsRequestDto){
        Response<RecurringBillsResponseDto> response = new Response<>();
        RecurringBillsResponseDto recurringBillsResponseDto = recurringBillsServiceImpl.updateRecurringBills(id, recurringBillsRequestDto);
        if(recurringBillsResponseDto!=null){
            response.successResponse(recurringBillsResponseDto,"recurring bills updated successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.failureResponse("invalid id");
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/recurring-bills/{id}")
    public ResponseEntity<Response<String>> deleteRecurringBills(@PathVariable int id) {
        Response<String> response = new Response<>();
        String data = recurringBillsServiceImpl.deleteRecurringBills(id);
        if (data.equals("success")) {
            response.successResponse(data,"recurring bills deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("Invalid id");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
