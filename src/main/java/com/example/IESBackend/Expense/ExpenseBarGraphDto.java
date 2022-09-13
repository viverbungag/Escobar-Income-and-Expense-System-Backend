package com.example.IESBackend.Expense;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExpenseBarGraphDto {

    private String expenseMonth;
    private Double monthlyIncome;
    private Double monthlyExpenses;
}
