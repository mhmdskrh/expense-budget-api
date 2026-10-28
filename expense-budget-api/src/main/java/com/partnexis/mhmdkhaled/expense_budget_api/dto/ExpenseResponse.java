package com.partnexis.mhmdkhaled.expense_budget_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseResponse(
        Integer id,
        BigDecimal amount,
        String description,
        LocalDate expenseDate,
        String categoryName,
        Integer userID
) {

}
