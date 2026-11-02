package com.partnexis.mhmdkhaled.expense_budget_api.dto;

import java.math.BigDecimal;

public record SummaryResponse(
        //String month,
        String category,
        BigDecimal budget,
        BigDecimal amountSpent,
        BigDecimal remaining) {

}
