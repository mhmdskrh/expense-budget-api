package com.partnexis.mhmdkhaled.expense_budget_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.partnexis.mhmdkhaled.expense_budget_api.entity.ExpenseCategory;
import java.util.List;


public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Integer> {
    Optional<ExpenseCategory> findByName(String name);
    List<ExpenseCategory> findAllByUserId(Integer userId);
    List<ExpenseCategory> findAllByUserEmail(String email);
    Optional<ExpenseCategory> findByNameAndUserEmail(String name, String email);

    Optional<ExpenseCategory> findByUserId(Integer userId);
}
