package com.nirmal.personalfinancetracker.repository;

import com.nirmal.personalfinancetracker.model.RecurringBills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecurringBillsRepository extends JpaRepository<RecurringBills, Integer> {
}
