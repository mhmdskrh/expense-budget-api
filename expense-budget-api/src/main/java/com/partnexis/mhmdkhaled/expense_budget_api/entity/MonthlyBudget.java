package com.partnexis.mhmdkhaled.expense_budget_api.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
create table monthly_budget(
    monthly_budget_id serial primary key,
    category_id integer references expense_category(category_id) on delete cascade,
    month varchar not null,
    amount money not null,
    constraint unique_monthly_budget unique(category_id, month)
);
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "monthly_budget", uniqueConstraints = {
        @UniqueConstraint(name = "unique_monthly_budget", columnNames = { "category_id", "month" }) })
public class MonthlyBudget {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monthly_budget_id")
    private Integer monthlyBudgetId;

    @Column(nullable = false)
    private String month;
    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ExpenseCategory category;
}