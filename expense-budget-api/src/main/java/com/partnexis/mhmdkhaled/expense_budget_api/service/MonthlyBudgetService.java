package com.partnexis.mhmdkhaled.expense_budget_api.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.partnexis.mhmdkhaled.expense_budget_api.dto.MonthlyBudgetRequest;
import com.partnexis.mhmdkhaled.expense_budget_api.dto.MonthlyBudgetResponse;
import com.partnexis.mhmdkhaled.expense_budget_api.entity.ExpenseCategory;
import com.partnexis.mhmdkhaled.expense_budget_api.entity.MonthlyBudget;
import com.partnexis.mhmdkhaled.expense_budget_api.repository.ExpenseCategoryRepository;
import com.partnexis.mhmdkhaled.expense_budget_api.repository.MonthlyBudgetRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MonthlyBudgetService {
    private final MonthlyBudgetRepository mBRepo;
    private final ExpenseCategoryRepository exCatRepo;

    public List<MonthlyBudgetResponse> getAll() {
        return mBRepo.findAllByCategoryUserEmail(getUserEmail()).stream().map(this::toResponse).toList();
    }

    public List<MonthlyBudgetResponse> getAllByCategory(String category) {
        return getAll().stream().filter(mb -> mb.category() == category).toList();
    }

    public MonthlyBudgetResponse getById(Integer id) throws Exception {
        MonthlyBudget mb = mBRepo.findAllByCategoryUserEmail(getUserEmail()).stream()
                .filter((budget) -> budget.getId() == id).findFirst()
                .orElseThrow(() -> new Exception("No such budget"));
        return toResponse(mb);
    }

    public MonthlyBudgetResponse create(MonthlyBudgetRequest request) throws Exception {
        /*
         * UserDetails userDetails = (UserDetails)
         * SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         * 
         * String username = userDetails.getUsername();
         * ExpenseCategory category =
         * exCatRepo.findByNameAndUserEmail(request.category(), username).orElseThrow();
         */
        String categoryName = request.category();

        if (mBRepo.findByCategoryIdAndMonth(getCategory(categoryName).getId(), "10/2026") != null) {
            throw new Exception("A budget already set for this month");
        }

        MonthlyBudget budget = new MonthlyBudget();

        budget.setMonth(request.month());
        budget.setAmount(request.amount());
        budget.setCategory(getCategory(categoryName));

        return toResponse(budget);
    }

    public MonthlyBudgetResponse update(Integer id, MonthlyBudgetRequest request) throws Exception {
        MonthlyBudget budget = mBRepo.findByIdAndCategoryUser_Email(id, getUserEmail()).orElseThrow(() -> new Exception("no such budget"));

        budget.setMonth(request.month());
        budget.setAmount(request.amount());
        budget.setCategory(getCategory(request.category()));

        return toResponse(budget);
    }

    public void delete(Integer id) throws Exception {
        MonthlyBudget mb = mBRepo.findByIdAndCategoryUser_Email(id, getUserEmail()).orElseThrow(() -> new Exception("No such budget"));
        mBRepo.delete(mb);
    }

    private MonthlyBudgetResponse toResponse(MonthlyBudget mB) {
        return new MonthlyBudgetResponse(mB.getId(), mB.getMonth(), mB.getAmount(),
                mB.getCategory().getName());
    }

    private String getUserEmail() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    private ExpenseCategory getCategory(String categoryName) {
        return exCatRepo.findByNameAndUserEmail(categoryName, getUserEmail()).orElse(null);
    }
}