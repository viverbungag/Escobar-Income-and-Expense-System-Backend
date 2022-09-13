package com.example.IESBackend.Expense;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("expense_jdbc_mysql")
public class ExpenseJdbcMySqlRepository implements ExpenseDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExpenseJdbcMySqlRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private ExpenseDto mapExpenseResult(final ResultSet rs) throws SQLException {
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setExpenseCategoryName("Transaction");
        return expenseDto;
    }

    public List<ExpenseDto> getAllExpensesByMonth(){
        String query = """
         SELECT DATE_FORMAT(transaction_date, '%M %Y'), SUM(price_per_unit * transaction_supply_quantity)
         FROM transaction WHERE transaction_type = 'STOCK_IN'
         GROUP BY DATE_FORMAT(transaction_date, '%M %Y');
        """;

        List<ExpenseDto> transactionExpenses = jdbcTemplate.query(query, (rs, rowNum) -> mapExpenseResult(rs));

        return transactionExpenses;
    }

    private ExpenseBarGraphDto mapExpenseBarGraphDateResult(final ResultSet rs) throws SQLException {
        ExpenseBarGraphDto expenseBarGraphDto = new ExpenseBarGraphDto();
        expenseBarGraphDto.setExpenseMonth(rs.getString("expense_date"));
        expenseBarGraphDto.setMonthlyExpenses(rs.getDouble("expense"));
        expenseBarGraphDto.setMonthlyIncome(rs.getDouble("income"));
        return expenseBarGraphDto;
    }

    public List<ExpenseBarGraphDto> getGraphDataByMonth(){
        String query = """
                SELECT expense_date, SUM(expense_cost) as expense, income
                FROM (
                	SELECT DATE_FORMAT(transaction_date, '%M %Y') AS expense_date, SUM(price_per_unit * transaction_supply_quantity) AS expense_cost
                		FROM transaction WHERE transaction_type = 'STOCK_IN'
                		GROUP BY expense_date
                 UNION
                	SELECT DATE_FORMAT(expense_date, '%M %Y') AS expense_date, SUM(expense_cost) AS expense_cost FROM expense
                		GROUP BY expense_date
                ) AS expense
                 INNER JOIN(
                	SELECT DATE_FORMAT(order_time, '%M %Y') AS income_date, SUM(total_cost) AS income
                		FROM customer_food_order
                		INNER JOIN customer_order ON customer_food_order.order_id = customer_order.order_id
                		GROUP BY order_time) AS income ON expense_date = income_date
                GROUP BY expense_date;
                """;

        List<ExpenseBarGraphDto> barGraphData = jdbcTemplate.query(query, (rs, rowNum) -> mapExpenseBarGraphDateResult(rs));

        return barGraphData;
    };

}
