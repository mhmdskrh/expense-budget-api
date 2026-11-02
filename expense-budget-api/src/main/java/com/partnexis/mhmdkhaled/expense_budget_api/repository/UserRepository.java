package com.partnexis.mhmdkhaled.expense_budget_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.partnexis.mhmdkhaled.expense_budget_api.entity.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
