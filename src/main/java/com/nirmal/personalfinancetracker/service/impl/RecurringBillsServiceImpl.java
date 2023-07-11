package com.nirmal.personalfinancetracker.service.impl;

import com.nirmal.personalfinancetracker.dto.request.RecurringBillsRequestDto;
import com.nirmal.personalfinancetracker.dto.response.RecurringBillsResponseDto;
import com.nirmal.personalfinancetracker.model.RecurringBills;
import com.nirmal.personalfinancetracker.model.User;
import com.nirmal.personalfinancetracker.repository.RecurringBillsRepository;
import com.nirmal.personalfinancetracker.repository.UserRepository;
import com.nirmal.personalfinancetracker.service.DtoMapper;
import com.nirmal.personalfinancetracker.service.RecurringBillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecurringBillsServiceImpl implements RecurringBillsService {

    @Autowired
    private RecurringBillsRepository recurringBillsRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private DtoMapper dtoMapper;

    @Override
    public RecurringBillsResponseDto addRecurringBills(RecurringBillsRequestDto recurringBillsRequestDto){
        RecurringBills recurringBills = new RecurringBills();
        User user = userServiceImpl.getCurrentUser();
        recurringBills.setUser(user);
        recurringBills.setName(recurringBillsRequestDto.getName());
        recurringBills.setRecurrence(recurringBillsRequestDto.getInterval());
        recurringBills.setAmount(recurringBillsRequestDto.getAmount());

        recurringBillsRepository.save(recurringBills);

        return dtoMapper.toRecurringBillsDto(recurringBills);
    }
    @Override
    public List<RecurringBillsResponseDto> viewRecurringBills(){
        User user = userServiceImpl.getCurrentUser();
        List<RecurringBills> recurringBills = recurringBillsRepository.findAllByUserId(user.getId());
        List<RecurringBillsResponseDto> recurringBillsResponseDtos = new ArrayList<>();
        for (RecurringBills bill:
             recurringBills) {
            recurringBillsResponseDtos.add(dtoMapper.toRecurringBillsDto(bill));
        }
        return recurringBillsResponseDtos;
    }

    @Override
    public RecurringBillsResponseDto recurringBillsById(int recurringBillsId){
        User user = userServiceImpl.getCurrentUser();
        Optional<RecurringBills> recurringBillsOptional = recurringBillsRepository.findById(recurringBillsId);
        if(recurringBillsOptional.isPresent() && recurringBillsOptional.get().getUser().getId() == user.getId()){
            return dtoMapper.toRecurringBillsDto(recurringBillsOptional.get());
        }
        return null;
    }

    @Override
    public RecurringBillsResponseDto updateRecurringBills(int recurringBillsId, RecurringBillsRequestDto recurringBillsRequestDto){
        Optional<RecurringBills> recurringBillsOptional = recurringBillsRepository.findById(recurringBillsId);
        User user = userServiceImpl.getCurrentUser();
        if(recurringBillsOptional.isPresent() && recurringBillsOptional.get().getUser().getId() == user.getId()){
            RecurringBills recurringBills = recurringBillsOptional.get();
            recurringBills.setUser(user);
            recurringBills.setName(recurringBillsRequestDto.getName());
            recurringBills.setRecurrence(recurringBillsRequestDto.getInterval());
            recurringBills.setAmount(recurringBillsRequestDto.getAmount());
            recurringBills.setId(recurringBillsId);

            recurringBillsRepository.save(recurringBills);

            return dtoMapper.toRecurringBillsDto(recurringBills);
        }
        return null;
    }
    @Override
    public String deleteRecurringBills(int recurringBillsId){
        User user = userServiceImpl.getCurrentUser();
        Optional<RecurringBills> recurringBillsOptional = recurringBillsRepository.findById(recurringBillsId);
        if(recurringBillsOptional.isPresent() && recurringBillsOptional.get().getUser().getId() == user.getId()){
            recurringBillsRepository.deleteById(recurringBillsId);
            return "success";
        }
        return "failed";
    }


}
