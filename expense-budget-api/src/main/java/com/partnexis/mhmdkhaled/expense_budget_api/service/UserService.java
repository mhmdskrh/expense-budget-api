package com.partnexis.mhmdkhaled.expense_budget_api.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.partnexis.mhmdkhaled.expense_budget_api.dto.LoginRequest;
import com.partnexis.mhmdkhaled.expense_budget_api.dto.LoginResponse;
import com.partnexis.mhmdkhaled.expense_budget_api.dto.RegisterRequest;
import com.partnexis.mhmdkhaled.expense_budget_api.entity.User;
import com.partnexis.mhmdkhaled.expense_budget_api.enums.Role;
import com.partnexis.mhmdkhaled.expense_budget_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository uRepo;
    private final PasswordEncoder passEncoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public LoginResponse register(RegisterRequest request) throws Exception {
        if (uRepo.findByEmail(request.email()) != null) {
            throw new Exception("Email already used");
        }

        User user = User.builder().name(request.name()).email(request.email())
                .password(passEncoder.encode(request.password())).role(Role.USER).build();

        uRepo.save(user);

        var token = jwtService.generateToken(user);

        return new LoginResponse(token);

        // TODO: configure jwt and return in login response
    }

    public LoginResponse login(LoginRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var user = uRepo.findByEmail(request.email()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        var token = jwtService.generateToken(user);
        return new LoginResponse(token);
    }
}
