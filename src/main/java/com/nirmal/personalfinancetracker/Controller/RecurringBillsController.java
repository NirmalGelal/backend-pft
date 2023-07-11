package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.request.RecurringBillsRequestDto;
import com.nirmal.personalfinancetracker.dto.response.RecurringBillsResponseDto;
import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.service.impl.RecurringBillsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecurringBillsController {
    @Autowired
    private RecurringBillsServiceImpl recurringBillsServiceImpl;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/bill")
    public ResponseEntity<Response<List<RecurringBillsResponseDto>>> viewRecurringBillsList() {
        Response<List<RecurringBillsResponseDto>> response = new Response<>();
        List<RecurringBillsResponseDto> recurringBillsResponseDtos = recurringBillsServiceImpl.viewRecurringBills();
        response.successResponse(recurringBillsResponseDtos, "list retrieved successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/bill/{id}")
    public ResponseEntity<Response<RecurringBillsResponseDto>> viewRecurringBillsById(@PathVariable int id) {
        Response<RecurringBillsResponseDto> response = new Response<>();
        RecurringBillsResponseDto recurringBillsResponseDto = recurringBillsServiceImpl.recurringBillsById(id);
        if (recurringBillsResponseDto != null) {
            response.successResponse(recurringBillsResponseDto, "bill retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("user unauthorized");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/bill")
    public ResponseEntity<Response<RecurringBillsResponseDto>> addRecurringBills(@RequestBody RecurringBillsRequestDto recurringBillsRequestDto) {
        Response<RecurringBillsResponseDto> response = new Response<>();
        RecurringBillsResponseDto recurringBillsResponseDto = recurringBillsServiceImpl.addRecurringBills(recurringBillsRequestDto);
        response.successResponse(recurringBillsResponseDto, "recurring bill added");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/bill/{recurringBillsId}")
    public ResponseEntity<Response<RecurringBillsResponseDto>> updateRecurringBills(@PathVariable int recurringBillsId,
                                                                                    @RequestBody RecurringBillsRequestDto recurringBillsRequestDto) {
        Response<RecurringBillsResponseDto> response = new Response<>();
        RecurringBillsResponseDto recurringBillsResponseDto = recurringBillsServiceImpl.updateRecurringBills(recurringBillsId, recurringBillsRequestDto);
        if (recurringBillsResponseDto != null) {
            response.successResponse(recurringBillsResponseDto, "recurring bills updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("user unauthorized");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/bill/{recurringBillsId}")
    public ResponseEntity<Response<String>> deleteRecurringBills(@PathVariable int recurringBillsId) {
        Response<String> response = new Response<>();
        String data = recurringBillsServiceImpl.deleteRecurringBills(recurringBillsId);
        if (data.equals("success")) {
            response.successResponse(data, "recurring bills deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.failureResponse("user unauthorized");
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
