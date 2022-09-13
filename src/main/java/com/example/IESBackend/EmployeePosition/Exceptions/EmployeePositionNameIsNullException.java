package com.example.IESBackend.EmployeePosition.Exceptions;

public class EmployeePositionNameIsNullException extends RuntimeException {

    public EmployeePositionNameIsNullException(){
        super("Position Name should not be empty");
    }
}
