package com.example.IESBackend.Expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        return expenseJdbcRepository.getBarGraphDataByMonth();
    }
}
