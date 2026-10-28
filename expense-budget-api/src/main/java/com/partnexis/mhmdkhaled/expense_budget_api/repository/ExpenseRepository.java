package com.partnexis.mhmdkhaled.expense_budget_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.partnexis.mhmdkhaled.expense_budget_api.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

}
