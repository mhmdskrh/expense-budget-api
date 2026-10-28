package com.partnexis.mhmdkhaled.expense_budget_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseRequest(
        BigDecimal amount,
        String description,
        LocalDate expenseDate,
        String category) {

}
