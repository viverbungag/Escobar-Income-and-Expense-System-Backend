package com.example.IESBackend.Expense;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/expense")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @PostMapping
    public List<ExpenseDto> getAllExpensesByMonth(){
        return expenseService.getAllExpensesByMonth();
    }

    @PostMapping("/transaction")
    public List<ExpenseTransactionDto> getAllExpenseTransactionByMonth(){
        return expenseService.getAllExpensesTransactionByMonth();
    }

    @PostMapping("/vertical-bar-graph")
    public List<ExpenseBarGraphDto> getBarGraphDataByMonth(@RequestBody FromToDate fromToDate){
        return expenseService.getBarGraphDataByMonth(fromToDate);
    }

    @PostMapping("/donut-graph")
    public List<ExpenseDonutGraphDto> getDonutGraphDataByMonth(@RequestBody FromToDate fromToDate){
        return expenseService.getDonutGraphDataByMonth(fromToDate);
    }

    @PostMapping("/add")
    public void addExpense(@RequestBody ExpenseDto expenseDto){
        expenseService.addExpense(expenseDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteExpense(@PathVariable Long id){
        expenseService.deleteExpense(id);
    }

    @PostMapping("/update/{id}")
    public void updateExpense(@RequestBody ExpenseDto expenseDto,
                              @PathVariable Long id){
        expenseService.updateExpense(id, expenseDto);
    }

    @PostMapping("/income")
    public List<IncomeDto> getAllIncomeByMonth(){
        return expenseService.getAllIncomeByMonth();
    }
}
