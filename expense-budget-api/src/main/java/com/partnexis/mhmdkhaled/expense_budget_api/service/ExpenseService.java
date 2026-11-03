package com.partnexis.mhmdkhaled.expense_budget_api.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.partnexis.mhmdkhaled.expense_budget_api.dto.ExpenseRequest;
import com.partnexis.mhmdkhaled.expense_budget_api.dto.ExpenseResponse;
import com.partnexis.mhmdkhaled.expense_budget_api.entity.Expense;
import com.partnexis.mhmdkhaled.expense_budget_api.entity.ExpenseCategory;
import com.partnexis.mhmdkhaled.expense_budget_api.repository.ExpenseCategoryRepository;
import com.partnexis.mhmdkhaled.expense_budget_api.repository.ExpenseRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepo;
    private final ExpenseCategoryRepository exCatRepo;

    public List<ExpenseResponse> getAll() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return expenseRepo.findAllByCategoryUserEmail(userDetails.getUsername()).stream().map(this::toResponse)
                .toList();
    }

    public List<ExpenseResponse> getAllByMonth(String mmyyyy) {
        String[] splitDate = mmyyyy.split("-");
        int month = Integer.parseInt(splitDate[0]);
        int year = Integer.parseInt(splitDate[1]);
        List<Expense> expensesByMonth = expenseRepo.findAllByMonth(month, year);
        Stream<Expense> expenses = expensesByMonth.stream()
                .filter((e) -> e.getCategory().getUser().getUsername() == getUserEmail());
        return expenses.map(this::toResponse)
                .toList();
    }

    public ExpenseResponse create(ExpenseRequest request) throws Exception {
        ExpenseCategory category = exCatRepo.findByNameAndUserEmail(request.category(), getUserEmail())
                .orElseThrow(() -> new Exception("No category with this name"));

        Expense expense = new Expense();

        expense.setAmount(request.amount());
        expense.setDescription(request.description());
        expense.setCategory(category);
        expense.setExpenseDate(getExpenseDate(request.expenseDate()));

        return toResponse(expenseRepo.save(expense));
    }

    public ExpenseResponse update(Integer id, ExpenseRequest request) throws Exception {
        Expense expense = expenseRepo.findById(id).orElseThrow(() -> new Exception("expense not found"));
        ExpenseCategory category = exCatRepo.findByNameAndUserEmail(request.category(), getUserEmail())
                .orElseThrow(() -> new Exception("No category with this name"));

        expense.setAmount(request.amount());
        expense.setDescription(request.description());
        expense.setCategory(category);
        expense.setExpenseDate(getExpenseDate(request.expenseDate()));

        return toResponse(expenseRepo.save(expense));
    }

    public void delete(Integer id) throws Exception {
        Expense expense = expenseRepo.findAllByCategoryUserEmail(getUserEmail()).stream()
                .filter((e) -> e.getId() == id).findFirst()
                .orElseThrow(() -> new Exception("Expense Not Found"));
        expenseRepo.delete(expense);
    }

    private ExpenseResponse toResponse(Expense ex) {
        return new ExpenseResponse(
                ex.getId(),
                ex.getAmount(),
                ex.getExpenseDate().toString(),
                ex.getDescription(),
                ex.getCategory().getName(),
                ex.getCategory().getUser().getId());
    }

    private LocalDate getExpenseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return !dateString.isEmpty()
                ? LocalDate.parse(dateString, formatter)
                : LocalDate.now();
    }

    private String getUserEmail() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
