package com.partnexis.mhmdkhaled.expense_budget_api.models;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Summary {
    //private String month;
    private String categoryName;
    private BigDecimal budget;
    private BigDecimal amountSpent;
    private BigDecimal remaining;
}