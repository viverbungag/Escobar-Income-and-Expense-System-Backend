package com.example.IESBackend.ExpenseCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ExpenseCategoryDao {

    Page<ExpenseCategory> getAllExpenseCategories(Pageable pageable);

    List<ExpenseCategory> getAllActiveExpenseCategoriesList();

    Page<ExpenseCategory> getAllActiveExpenseCategories(Pageable pageable);

    Page<ExpenseCategory> getAllInactiveExpenseCategories(Pageable pageable);

    void inactivateExpenseCategory(List<String> expenseCategoryNames);

    void activateExpenseCategory(List<String> expenseCategoryNames);

    void insertExpenseCategory(String expenseCategoryName, Boolean isActive);

    Optional<ExpenseCategory> getExpenseCategoryById(Long expenseCategoryId);

    Optional<ExpenseCategory> getExpenseCategoryByName(String expenseCategoryName);
}
