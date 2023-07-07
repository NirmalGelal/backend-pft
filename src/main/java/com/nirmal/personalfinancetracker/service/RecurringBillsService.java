package com.nirmal.personalfinancetracker.service;

import com.nirmal.personalfinancetracker.dto.request.RecurringBillsRequestDto;
import com.nirmal.personalfinancetracker.dto.response.RecurringBillsResponseDto;
import com.nirmal.personalfinancetracker.model.RecurringBills;

import java.util.List;

public interface RecurringBillsService {
    public RecurringBillsResponseDto addRecurringBills(RecurringBillsRequestDto recurringBillsRequestDto);
    public List<RecurringBillsResponseDto> viewRecurringBills();
    public RecurringBillsResponseDto recurringBillsById(int recurringBillsId);
    public RecurringBillsResponseDto updateRecurringBills(int recurringBillsId, RecurringBillsRequestDto recurringBillsRequestDto);
    public String deleteRecurringBills(int recurringBillsId);

}
