package com.example.IESBackend.ExpenseCategory.Exceptions;

public class ExpenseCategoryNameIsExistingException extends RuntimeException {

    public ExpenseCategoryNameIsExistingException(String name){
        super(String.format("The Name %s is already existing", name));
    }
}
