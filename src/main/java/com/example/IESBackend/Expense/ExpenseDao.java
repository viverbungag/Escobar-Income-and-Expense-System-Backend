package com.example.IESBackend.Expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseDao {

    List<ExpenseTransactionDto> getAllTransactionExpensesByMonth();

    List<ExpenseDto> getAllExpensesByMonth();

    List<ExpenseBarGraphDto> getGraphDataByMonth();

    void addExpense(Long expenseCategoryId,
                           String expenseDescription,
                           LocalDateTime expenseDate,
                           BigDecimal expenseCost);

    void deleteExpense(Long expenseId);

    List<IncomeDto> getAllIncomeByMonth();

}
