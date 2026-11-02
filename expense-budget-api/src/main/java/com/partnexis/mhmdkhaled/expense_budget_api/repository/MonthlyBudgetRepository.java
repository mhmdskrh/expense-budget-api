package com.partnexis.mhmdkhaled.expense_budget_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.partnexis.mhmdkhaled.expense_budget_api.entity.ExpenseCategory;
import com.partnexis.mhmdkhaled.expense_budget_api.entity.MonthlyBudget;

public interface MonthlyBudgetRepository extends JpaRepository<MonthlyBudget, Integer> {
    Optional<MonthlyBudget> findByCategoryIdAndMonth(Integer categoryId, String month);

    List<MonthlyBudget> findByMonth(String month);

    Optional<MonthlyBudget> findByCategory(ExpenseCategory category);
    
    List<MonthlyBudget> findAllByCategoryUserEmail(String email); // join budget, category, user -> get by email

    Optional<MonthlyBudget> findByIdAndCategoryUser_Email(int id, String email);

    Optional<MonthlyBudget> findByCategoryIdAndMonthAndCategoryUser_Id(int categoryId, String month, int userId);
}
