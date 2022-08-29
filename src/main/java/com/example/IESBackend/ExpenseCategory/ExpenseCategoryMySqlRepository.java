package com.example.IESBackend.ExpenseCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("expenseCategory_mysql")
public interface ExpenseCategoryMySqlRepository {

    @Query(value = "SELECT * FROM #{#entityName}",
            nativeQuery = true)
    Page<ExpenseCategory> getAllExpenseCategories(Pageable pageable);

    @Query(value = "SELECT * FROM #{#entityName} WHERE is_active=true",
            nativeQuery = true)
    List<ExpenseCategory> getAllActiveExpenseCategoriesList();

    @Query(value = "SELECT * FROM #{#entityName} WHERE is_active=true",
            nativeQuery = true)
    Page<ExpenseCategory> getAllActiveExpenseCategories(Pageable pageable);

    @Query(value = "SELECT * FROM #{#entityName} WHERE is_active=false",
            nativeQuery = true)
    Page<ExpenseCategory> getAllInactiveExpenseCategories(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_active=false WHERE expense_category_name IN :expenseCategoryNames",
            nativeQuery = true)
    void inactivateExpenseCategory(List<String> expenseCategoryNames);

    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_active=true WHERE expense_category_name IN :expenseCategoryNames",
            nativeQuery = true)
    void activateExpenseCategory(List<String> expenseCategoryNames);

    @Modifying
    @Query(value = "INSERT INTO #{#entityName} " +
            "(expense_category_name, is_active) " +
            "VALUES (:expenseCategoryName, :isActive)",
            nativeQuery = true)
    void insertExpenseCategory(String expenseCategoryName, Boolean isActive);

    @Query(value = "SELECT * FROM #{#entityName} " +
            "WHERE expense_category_id = :expenseCategoryId",
            nativeQuery = true)
    Optional<ExpenseCategory> getExpenseCategoryById(Long expenseCategoryId);

    @Query(value = "SELECT * FROM #{#entityName} " +
            "WHERE expense_category_name = :expenseCategoryName",
            nativeQuery = true)
    Optional<ExpenseCategory> getExpenseCategoryByName(String expenseCategoryName);
}
