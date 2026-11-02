package com.partnexis.mhmdkhaled.expense_budget_api.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.partnexis.mhmdkhaled.expense_budget_api.dto.ExpenseCategoryRequest;
import com.partnexis.mhmdkhaled.expense_budget_api.dto.ExpenseCategoryResponse;
import com.partnexis.mhmdkhaled.expense_budget_api.entity.ExpenseCategory;
import com.partnexis.mhmdkhaled.expense_budget_api.entity.User;
import com.partnexis.mhmdkhaled.expense_budget_api.repository.ExpenseCategoryRepository;
import com.partnexis.mhmdkhaled.expense_budget_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// TODO: fetch userdetails from token

@RequiredArgsConstructor
@Service
public class ExpenseCategoryService {
    private final ExpenseCategoryRepository exCatRepo;
    private final UserRepository uRepo;

    public List<ExpenseCategoryResponse> getAll() {
        return exCatRepo.findAllByUserEmail(getUserEmail()).stream().map(this::toResponse).toList();
    }

    public ExpenseCategoryResponse create(ExpenseCategoryRequest request) throws Exception {

        ExpenseCategory exCat = new ExpenseCategory();
        User user = uRepo.findByEmail(getUserEmail()).orElseThrow(() -> new UsernameNotFoundException("no such user"));

        exCat.setName(request.name());
        exCat.setDescription(request.description());
        exCat.setUser(user);

        return toResponse(exCatRepo.save(exCat));
    }

    public ExpenseCategoryResponse update(ExpenseCategoryRequest request) throws Exception {

        ExpenseCategory exCat = exCatRepo.findByNameAndUserEmail(request.name(), getUserEmail())
                .orElseThrow(() -> new Exception("No category with this name"));
        User user = uRepo.findByEmail(getUserEmail()).orElseThrow(() -> new UsernameNotFoundException("no such user"));

        exCat.setName(request.name());
        exCat.setDescription(request.description());
        exCat.setUser(user);

        return toResponse(exCatRepo.save(exCat));
    }

    public void delete(ExpenseCategoryRequest request) throws Exception {
        ExpenseCategory exCat = exCatRepo.findByNameAndUserEmail(request.name(), getUserEmail())
                .orElseThrow(() -> new Exception("No category with this name"));

        exCatRepo.delete(exCat);
    }

    private ExpenseCategoryResponse toResponse(ExpenseCategory expenseCategory) {
        return new ExpenseCategoryResponse(expenseCategory.getCategoryId(), expenseCategory.getName(),
                expenseCategory.getDescription());
    }

    private String getUserEmail() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
