package com.example.IESBackend.Expense;

import java.util.List;

public interface ExpenseDao {

    List<ExpenseDto> getAllExpensesByMonth();

    List<ExpenseBarGraphDto> getBarGraphDataByMonth();
}
