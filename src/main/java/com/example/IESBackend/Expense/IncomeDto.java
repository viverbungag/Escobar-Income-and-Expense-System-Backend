package com.example.IESBackend.Expense;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class IncomeDto {

    private String incomeDate;
    private BigDecimal dailyIncome;
}
