package com.partnexis.mhmdkhaled.expense_budget_api.dto;

import java.math.BigDecimal;

public record ExpenseResponse(
                Integer id,
                BigDecimal amount,
                String description,
                String expenseDate,
                String categoryName,
                Integer userID) {
}
