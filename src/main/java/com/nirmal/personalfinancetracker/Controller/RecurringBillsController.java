package com.nirmal.personalfinancetracker.Controller;

import com.nirmal.personalfinancetracker.dto.request.RecurringBillsDto;
import com.nirmal.personalfinancetracker.dto.response.Response;
import com.nirmal.personalfinancetracker.model.RecurringBills;
import com.nirmal.personalfinancetracker.service.impl.RecurringBillsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class RecurringBillsController {
    @Autowired
    private RecurringBillsServiceImpl recurringBillsServiceImpl;
    @GetMapping("/recurring-bills")
    public ResponseEntity<Response<List<RecurringBills>>> viewRecurringBillsList(){
        Response<List<RecurringBills>> response = new Response<>();
        List<RecurringBills> bills = recurringBillsServiceImpl.viewRecurringBills();
        if(!bills.isEmpty()){
            response.setStatus(true);
            response.setMessage("list retrieved successfully");
            response.setData(bills);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setData(null);
        response.setStatus(false);
        response.setMessage("no bills in database");
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @GetMapping("/recurring-bills/{id}")
    public ResponseEntity<Response<RecurringBills>> viewRecurringBillsById(@PathVariable int id){
        Response<RecurringBills> response = new Response<>();
        RecurringBills recurringBills =  recurringBillsServiceImpl.recurringBillsById(id);
        if (recurringBills!=null){
            response.setStatus(true);
            response.setMessage("bill retrieved successfully");
            response.setData(recurringBills);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.setStatus(false);
        response.setMessage("invalid id");
        response.setData(null);
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @PostMapping("/recurring-bills")
    public ResponseEntity<Response<RecurringBills>> addRecurringBills(@RequestBody RecurringBillsDto recurringBillsDto){
        Response<RecurringBills> response = new Response<>();
        RecurringBills recurringBills = recurringBillsServiceImpl.addRecurringBills(recurringBillsDto);
        response.setStatus(true);
        response.setMessage("recurring bill added");
        response.setData(recurringBills);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/recurring-bills/{id}")
    public ResponseEntity<Response<RecurringBills>> updateRecurringBills(@PathVariable int id,
                                                                         @RequestBody RecurringBillsDto recurringBillsDto){
        Response<RecurringBills> response = new Response<>();
        RecurringBills recurringBills = recurringBillsServiceImpl.updateRecurringBills(id,recurringBillsDto);
        if(recurringBills!=null){
            response.setMessage("recurring bills updated successfully");
            response.setStatus(true);
            response.setData(recurringBills);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.setData(null);
        response.setMessage("invalid id");
        response.setStatus(false);
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/recurring-bills/{id}")
    public ResponseEntity<Response<String>> deleteRecurringBills(@PathVariable int id) {
        Response<String> response = new Response<>();
        String data = recurringBillsServiceImpl.deleteRecurringBills(id);
        if (data == "success") {
            response.setData(data);
            response.setStatus(true);
            response.setMessage("recurring bills deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setStatus(false);
        response.setData("Invalid id");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
