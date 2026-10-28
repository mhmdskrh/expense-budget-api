package com.partnexis.mhmdkhaled.expense_budget_api.dto;

public record RegisterRequest(
        String name,
        String email,
        String password) {
}
