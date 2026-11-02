package com.partnexis.mhmdkhaled.expense_budget_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.partnexis.mhmdkhaled.expense_budget_api.dto.ExpenseRequest;
import com.partnexis.mhmdkhaled.expense_budget_api.dto.ExpenseResponse;
import com.partnexis.mhmdkhaled.expense_budget_api.service.ExpenseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService exService;

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAll() {
        return ResponseEntity.ok(exService.getAll());
    }

    @GetMapping("/{month}")
    public ResponseEntity<List<ExpenseResponse>> getByName(@PathVariable String month) throws Exception {
        return ResponseEntity.ok(exService.getAllByMonth(month));
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> create(@RequestBody ExpenseRequest request)
            throws Exception {
        ExpenseResponse response = exService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> update(@PathVariable Integer id,
            @RequestBody ExpenseRequest request) throws Exception {
        return ResponseEntity.ok(exService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception{
        exService.delete(id);
        return ResponseEntity.noContent().build();
    } 
}
