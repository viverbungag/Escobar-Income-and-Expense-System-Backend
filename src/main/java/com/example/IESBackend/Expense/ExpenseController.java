package com.example.IESBackend.Expense;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/expense")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @PostMapping("/by-month")
    public List<ExpenseDto> getAllExpensesByMonth(){
        return expenseService.getAllExpensesByMonth();
    }

    @PostMapping("/bar-graph")
    public List<ExpenseBarGraphDto> getBarGraphDataByMonth(){
        return expenseService.getBarGraphDataByMonth();
    }
}
