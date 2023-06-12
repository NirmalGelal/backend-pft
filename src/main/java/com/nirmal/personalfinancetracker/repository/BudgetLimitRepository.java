package com.nirmal.personalfinancetracker.repository;

import com.nirmal.personalfinancetracker.enums.ExpenseEnum;
import com.nirmal.personalfinancetracker.enums.RecurrenceEnum;
import com.nirmal.personalfinancetracker.model.BudgetLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BudgetLimitRepository extends JpaRepository<BudgetLimit, Integer> {

    @Query("select b from BudgetLimit b where b.category=:category and b.interval=:interval")
    public Optional<BudgetLimit> findByCategoryAndInterval(ExpenseEnum category, RecurrenceEnum interval);
}
