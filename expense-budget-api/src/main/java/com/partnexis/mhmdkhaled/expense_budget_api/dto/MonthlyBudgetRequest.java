package com.partnexis.mhmdkhaled.expense_budget_api.dto;

import java.math.BigDecimal;

public record MonthlyBudgetRequest(
        String month, // MM-yy
        BigDecimal amount,
        String category) {

}
