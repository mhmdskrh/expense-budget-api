package com.partnexis.mhmdkhaled.expense_budget_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.partnexis.mhmdkhaled.expense_budget_api.dto.SummaryResponse;
import com.partnexis.mhmdkhaled.expense_budget_api.service.SummaryService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/summary")
public class SummaryController {
    private final SummaryService sService;

    @GetMapping("/{month}")
    public ResponseEntity<List<SummaryResponse>> getMonthlySummary(@PathVariable  String month) {
        return ResponseEntity.ok(sService.getMonthlySummary(month));
    }
    
}
