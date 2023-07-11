package com.nirmal.personalfinancetracker.repository;

import com.nirmal.personalfinancetracker.enums.ExpenseEnum;
import com.nirmal.personalfinancetracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findAllByUserId(int userId);
    @Query("SELECT e FROM Expense e WHERE e.category = :category AND e.createdAt >= :start AND e.createdAt <= :end")
    public List<Expense> findTotalExpense(@Param("category") ExpenseEnum category, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
