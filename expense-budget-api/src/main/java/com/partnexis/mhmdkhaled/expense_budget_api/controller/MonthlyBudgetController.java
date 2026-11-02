package com.partnexis.mhmdkhaled.expense_budget_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.partnexis.mhmdkhaled.expense_budget_api.dto.MonthlyBudgetRequest;
import com.partnexis.mhmdkhaled.expense_budget_api.dto.MonthlyBudgetResponse;
import com.partnexis.mhmdkhaled.expense_budget_api.service.MonthlyBudgetService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RequiredArgsConstructor
@RestController
@RequestMapping("/api/budgets")
public class MonthlyBudgetController {
    private final MonthlyBudgetService mBService;

    @GetMapping
    public ResponseEntity<List<MonthlyBudgetResponse>> getAll() {
        return ResponseEntity.ok(mBService.getAll());
    }
    
    @GetMapping("/{category}")
    public ResponseEntity<List<MonthlyBudgetResponse>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(mBService.getAllByCategory(category));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MonthlyBudgetResponse> getByID(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(mBService.getById(id));
    }
    
    @PostMapping
    public ResponseEntity<MonthlyBudgetResponse> create(@RequestBody MonthlyBudgetRequest request) throws Exception {
        MonthlyBudgetResponse response = mBService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MonthlyBudgetResponse> update(@PathVariable Integer id, @RequestBody MonthlyBudgetRequest request) throws Exception {
        return ResponseEntity.ok(mBService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Integer id) throws Exception{
        mBService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
