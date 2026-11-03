package com.partnexis.mhmdkhaled.expense_budget_api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.partnexis.mhmdkhaled.expense_budget_api.dto.SummaryResponse;
import com.partnexis.mhmdkhaled.expense_budget_api.entity.Expense;
import com.partnexis.mhmdkhaled.expense_budget_api.entity.ExpenseCategory;
import com.partnexis.mhmdkhaled.expense_budget_api.entity.MonthlyBudget;
import com.partnexis.mhmdkhaled.expense_budget_api.models.Summary;
import com.partnexis.mhmdkhaled.expense_budget_api.repository.ExpenseCategoryRepository;
import com.partnexis.mhmdkhaled.expense_budget_api.repository.ExpenseRepository;
import com.partnexis.mhmdkhaled.expense_budget_api.repository.MonthlyBudgetRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SummaryService {
    private final ExpenseRepository exRepo;
    private final MonthlyBudgetRepository mBRepo;
    private final ExpenseCategoryRepository exCatRepo;

    public List<SummaryResponse> getMonthlySummary(String monthYear) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();

        List<Summary> summaries = new ArrayList<>();

        List<ExpenseCategory> categories = exCatRepo.findAllByUserEmail(email);
        for (ExpenseCategory expenseCategory : categories) {
            summaries.add(createSummaries(expenseCategory, monthYear));
        }

        return summaries.stream().map(this::toResponse).toList();
    }

    private SummaryResponse toResponse(Summary summary) {
        return new SummaryResponse(
                summary.getCategoryName(),
                summary.getBudget(),
                summary.getAmountSpent(),
                summary.getRemaining());
    }

    private Summary createSummaries(ExpenseCategory category, String monthYear) {
        String[] splitDate = monthYear.split("-");
        int month = Integer.parseInt(splitDate[0]);
        int year = Integer.parseInt(splitDate[1]);

        MonthlyBudget budget = mBRepo.findByCategory(category).orElse(null);
        double amountSpent = 0;

        double budgetAmount = budget == null ? null : budget.getAmount().doubleValue();
        List<Expense> expensesByCategory = exRepo.findAllByCategoryIdAndMonth(category.getId(), month, year);

        for (Expense expense : expensesByCategory) {
            amountSpent += expense.getAmount().doubleValue();
        }

        double remainingAmount = budget == null ? null : (budgetAmount - amountSpent);

        return new Summary(
                category.getName(),
                BigDecimal.valueOf(budgetAmount),
                BigDecimal.valueOf(amountSpent),
                BigDecimal.valueOf(remainingAmount));
    }
}
