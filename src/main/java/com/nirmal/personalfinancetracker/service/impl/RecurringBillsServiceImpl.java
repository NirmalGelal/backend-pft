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
    private UserRepository userRepository;
    @Autowired
    private DtoMapper dtoMapper;

    @Override
    public RecurringBillsResponseDto addRecurringBills(RecurringBillsRequestDto recurringBillsRequestDto){
        RecurringBills recurringBills = new RecurringBills();
        User user = userRepository.findById(recurringBillsRequestDto.getUserId()).get();
        recurringBills.setUser(user);
        recurringBills.setName(recurringBillsRequestDto.getName());
        recurringBills.setRecurrence(recurringBillsRequestDto.getInterval());
        recurringBills.setAmount(recurringBillsRequestDto.getAmount());

        recurringBillsRepository.save(recurringBills);

        return dtoMapper.toRecurringBillsDto(recurringBills);
    }
    @Override
    public List<RecurringBillsResponseDto> viewRecurringBills(){
        List<RecurringBills> recurringBills = recurringBillsRepository.findAll();
        List<RecurringBillsResponseDto> recurringBillsResponseDtos = new ArrayList<>();
        for (RecurringBills bill:
             recurringBills) {
            recurringBillsResponseDtos.add(dtoMapper.toRecurringBillsDto(bill));
        }
        return recurringBillsResponseDtos;
    }

    @Override
    public RecurringBillsResponseDto recurringBillsById(int recurringBillsId){
        Optional<RecurringBills> recurringBillsOptional = recurringBillsRepository.findById(recurringBillsId);
        return recurringBillsOptional.map(recurringBills -> dtoMapper.toRecurringBillsDto(recurringBills)).orElse(null);
    }

    @Override
    public RecurringBillsResponseDto updateRecurringBills(int recurringBillsId, RecurringBillsRequestDto recurringBillsRequestDto){
        Optional<RecurringBills> recurringBillsOptional = recurringBillsRepository.findById(recurringBillsId);
        if(recurringBillsOptional.isPresent()){
            RecurringBills recurringBills = recurringBillsOptional.get();
            recurringBills.setUser(userRepository.findById(recurringBillsRequestDto.getUserId()).get());
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
        Optional<RecurringBills> recurringBillsOptional = recurringBillsRepository.findById(recurringBillsId);
        if(recurringBillsOptional.isPresent()){
            recurringBillsRepository.deleteById(recurringBillsId);
            return "success";
        }
        return "recurring bills with id: "+recurringBillsId+ " not present.";
    }


}
