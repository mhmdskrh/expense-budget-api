package com.partnexis.mhmdkhaled.expense_budget_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.partnexis.mhmdkhaled.expense_budget_api.dto.ExpenseCategoryRequest;
import com.partnexis.mhmdkhaled.expense_budget_api.dto.ExpenseCategoryResponse;
import com.partnexis.mhmdkhaled.expense_budget_api.service.ExpenseCategoryService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class ExpenseCategoryController {
    private final ExpenseCategoryService exCatService;

    @GetMapping
    public ResponseEntity<List<ExpenseCategoryResponse>> getAll() {
        return ResponseEntity.ok(exCatService.getAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<ExpenseCategoryResponse> getByName(@PathVariable String name) throws Exception {
        return ResponseEntity.ok(exCatService.getByName(name));
    }

    @PostMapping
    public ResponseEntity<ExpenseCategoryResponse> create(@RequestBody ExpenseCategoryRequest request)
            throws Exception {
        ExpenseCategoryResponse response = exCatService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{name}")
    public ResponseEntity<ExpenseCategoryResponse> update(@PathVariable String name,
            @RequestBody ExpenseCategoryRequest request) throws Exception {
        return ResponseEntity.ok(exCatService.update(name, request));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) throws Exception{
        exCatService.delete(name);
        return ResponseEntity.noContent().build();
    } 
}
