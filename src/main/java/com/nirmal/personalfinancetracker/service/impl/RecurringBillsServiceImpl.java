package com.nirmal.personalfinancetracker.service.impl;

import com.nirmal.personalfinancetracker.dto.request.RecurringBillsDto;
import com.nirmal.personalfinancetracker.model.RecurringBills;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.repository.RecurringBillsRepository;
import com.nirmal.personalfinancetracker.repository.UserRepository;
import com.nirmal.personalfinancetracker.service.RecurringBillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecurringBillsServiceImpl implements RecurringBillsService {

    @Autowired
    private RecurringBillsRepository recurringBillsRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public RecurringBills addRecurringBills(RecurringBillsDto recurringBillsDto){
        RecurringBills recurringBills = new RecurringBills();
        User user = userRepository.findById(recurringBillsDto.getUserId()).get();
        recurringBills.setUser(user);
        recurringBills.setName(recurringBillsDto.getName());
        recurringBills.setRecurrence(recurringBillsDto.getInterval());
        recurringBills.setAmount(recurringBillsDto.getAmount());

        return recurringBillsRepository.save(recurringBills);
    }
    @Override
    public List<RecurringBills> viewRecurringBills(){
        return recurringBillsRepository.findAll();
    }

    @Override
    public RecurringBills recurringBillsById(int recurringBillsId){
        Optional<RecurringBills> recurringBillsOptional = recurringBillsRepository.findById(recurringBillsId);
        if(recurringBillsOptional.isPresent()){
            return recurringBillsOptional.get();
        }
        return null;
    }

    @Override
    public RecurringBills updateRecurringBills(int recurringBillsId, RecurringBillsDto recurringBillsDto){
        Optional<RecurringBills> recurringBillsOptional = recurringBillsRepository.findById(recurringBillsId);
        if(recurringBillsOptional.isPresent()){
            RecurringBills recurringBills = recurringBillsOptional.get();
            recurringBills.setUser(userRepository.findById(recurringBillsDto.getUserId()).get());
            recurringBills.setName(recurringBillsDto.getName());
            recurringBills.setRecurrence(recurringBillsDto.getInterval());
            recurringBills.setAmount(recurringBillsDto.getAmount());
            recurringBills.setId(recurringBillsId);

            return recurringBillsRepository.save(recurringBills);
        }
        return null;
    }
    @Override
    public String deleteRecurringBills(int recurringBillsId){
        Optional<RecurringBills> recurringBillsOptional = recurringBillsRepository.findById(recurringBillsId);
        if(recurringBillsOptional.isPresent()){
            recurringBillsRepository.deleteById(recurringBillsId);
            return "success";
        }
        return "recurring bills with id: "+recurringBillsId+ " not present.";
    }


}
