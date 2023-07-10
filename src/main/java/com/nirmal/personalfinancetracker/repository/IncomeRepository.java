package com.nirmal.personalfinancetracker.repository;

import com.nirmal.personalfinancetracker.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {
    List<Income> findAllByUserId(int id);
}
