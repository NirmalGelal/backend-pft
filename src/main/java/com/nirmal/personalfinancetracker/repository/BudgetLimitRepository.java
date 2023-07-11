package com.nirmal.personalfinancetracker.repository;

import com.nirmal.personalfinancetracker.enums.ExpenseEnum;
import com.nirmal.personalfinancetracker.enums.RecurrenceEnum;
import com.nirmal.personalfinancetracker.model.BudgetLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetLimitRepository extends JpaRepository<BudgetLimit, Integer> {
    List<BudgetLimit> findAllByUserId(int userId);

    @Query("select b from BudgetLimit b where b.category=:category and b.interval=:interval")
    Optional<BudgetLimit> findByCategoryAndInterval(ExpenseEnum category, RecurrenceEnum interval);
}
