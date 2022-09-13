package com.example.IESBackend.Expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ExpenseService {

    @Autowired
    @Qualifier("expense_jdbc_mysql")
    ExpenseDao expenseJdbcRepository;

    public List<ExpenseDto> getAllExpensesByMonth(){
        return expenseJdbcRepository.getAllExpensesByMonth();
    }

    public List<ExpenseBarGraphDto> getBarGraphDataByMonth() {
        return expenseJdbcRepository.getGraphDataByMonth();
    }

    public List<ExpenseDonutGraphDto> getDonutGraphDataByMonth(){
        List<ExpenseBarGraphDto> graphDataByMonth = expenseJdbcRepository.getGraphDataByMonth();
        Double totalExpense = graphDataByMonth.stream().reduce(0.0, (sum, data) -> sum + data.getMonthlyExpenses(), Double::sum);
        Double totalIncome = graphDataByMonth.stream().reduce(0.0, (sum, data) -> sum + data.getMonthlyIncome(), Double::sum);

        List<ExpenseDonutGraphDto> expenseDonutGraphDtoList = new ArrayList<>();

        ExpenseDonutGraphDto expenseDonutGraphDtoExpense = new ExpenseDonutGraphDto();
        expenseDonutGraphDtoExpense.setDonutLabel("Expense");
        expenseDonutGraphDtoExpense.setDonutData(totalExpense);


        ExpenseDonutGraphDto expenseDonutGraphDtoIncome = new ExpenseDonutGraphDto();
        expenseDonutGraphDtoIncome.setDonutLabel("Income");
        expenseDonutGraphDtoIncome.setDonutData(totalIncome);


        expenseDonutGraphDtoList.add(expenseDonutGraphDtoExpense);
        expenseDonutGraphDtoList.add(expenseDonutGraphDtoIncome);

        return expenseDonutGraphDtoList;
    }
}
