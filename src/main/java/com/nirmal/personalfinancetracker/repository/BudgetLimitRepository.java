package com.nirmal.personalfinancetracker.repository;

import com.nirmal.personalfinancetracker.model.BudgetLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetLimitRepository extends JpaRepository<BudgetLimit, Integer> {
}
