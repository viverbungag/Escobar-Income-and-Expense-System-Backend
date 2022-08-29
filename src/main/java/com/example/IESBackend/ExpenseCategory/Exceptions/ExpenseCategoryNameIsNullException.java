package com.example.IESBackend.ExpenseCategory.Exceptions;

public class ExpenseCategoryNameIsNullException extends RuntimeException {

    public ExpenseCategoryNameIsNullException(){
        super("Expense Category Name should not be empty");
    }
}
