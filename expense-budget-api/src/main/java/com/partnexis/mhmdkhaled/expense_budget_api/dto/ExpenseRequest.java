package com.partnexis.mhmdkhaled.expense_budget_api.dto;

import java.math.BigDecimal;

public record ExpenseRequest(
        BigDecimal amount,
        String description,
        String expenseDate,
        String category) {

}
