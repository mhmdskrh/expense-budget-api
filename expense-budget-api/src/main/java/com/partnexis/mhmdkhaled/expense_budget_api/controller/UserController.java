package com.partnexis.mhmdkhaled.expense_budget_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.partnexis.mhmdkhaled.expense_budget_api.dto.LoginRequest;
import com.partnexis.mhmdkhaled.expense_budget_api.dto.LoginResponse;
import com.partnexis.mhmdkhaled.expense_budget_api.dto.RegisterRequest;
import com.partnexis.mhmdkhaled.expense_budget_api.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest request) throws Exception {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }
}
