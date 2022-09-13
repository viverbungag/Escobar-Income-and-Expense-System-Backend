package com.example.IESBackend.Expense;


import com.example.IESBackend.ExpenseCategory.ExpenseCategory;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExpenseDto {

    private Long expenseId;
    private String expenseCategoryName;
    private String expenseDescription;
    private LocalDateTime expenseDate;
    private BigDecimal expenseCost;
}
