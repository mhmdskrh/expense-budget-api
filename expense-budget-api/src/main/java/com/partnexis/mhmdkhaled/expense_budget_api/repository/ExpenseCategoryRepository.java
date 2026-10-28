package com.partnexis.mhmdkhaled.expense_budget_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.partnexis.mhmdkhaled.expense_budget_api.entity.ExpenseCategory;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Integer> {

}
