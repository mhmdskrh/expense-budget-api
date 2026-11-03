package com.partnexis.mhmdkhaled.expense_budget_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.partnexis.mhmdkhaled.expense_budget_api.entity.Expense;
import com.partnexis.mhmdkhaled.expense_budget_api.entity.ExpenseCategory;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    @Query("SELECT e FROM Expense e WHERE YEAR(e.expenseDate) = :year AND MONTH(e.expenseDate) = :month")
    public List<Expense> findAllByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT e FROM Expense e WHERE e.category.id = :categoryId AND YEAR(e.expenseDate) = :year AND MONTH(e.expenseDate) = :month")
    public List<Expense> findAllByCategoryIdAndMonth(@Param("categoryId") int categoryId, @Param("month") int month,
            @Param("year") int year);

    public List<Expense> findAllByCategoryUserEmail(String email);

    List<Expense> findAllByCategory(ExpenseCategory category);
}
