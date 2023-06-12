package com.nirmal.personalfinancetracker.service;

import com.nirmal.personalfinancetracker.dto.request.RecurringBillsDto;
import com.nirmal.personalfinancetracker.model.RecurringBills;

import java.util.List;

public interface RecurringBillsService {
    public RecurringBills addRecurringBills(RecurringBillsDto recurringBillsDto);
    public List<RecurringBills> viewRecurringBills();
    public RecurringBills recurringBillsById(int recurringBillsId);
    public RecurringBills updateRecurringBills(int recurringBillsId, RecurringBillsDto recurringBillsDto);
    public String deleteRecurringBills(int recurringBillsId);

}
