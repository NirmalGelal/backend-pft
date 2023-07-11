package com.nirmal.personalfinancetracker.repository;

import com.nirmal.personalfinancetracker.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Integer> {
    List<Goal> findAllByUserId(int userId);
}
