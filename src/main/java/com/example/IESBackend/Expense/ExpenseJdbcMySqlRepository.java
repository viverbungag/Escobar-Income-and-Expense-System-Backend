package com.example.IESBackend.Expense;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository("expense_jdbc_mysql")
public class ExpenseJdbcMySqlRepository implements ExpenseDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExpenseJdbcMySqlRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private ExpenseTransactionDto mapExpenseTransactionResult(final ResultSet rs) throws SQLException {
        ExpenseTransactionDto expenseTransactionDto = new ExpenseTransactionDto();
        expenseTransactionDto.setTransactionId(rs.getLong("transaction_id"));
        expenseTransactionDto.setTransactionDate(rs.getObject("transaction_date", LocalDateTime.class));
        expenseTransactionDto.setSupplyName(rs.getString("supply_name"));

        BigDecimal pricePerUnit = rs.getObject("price_per_unit", BigDecimal.class);
        BigDecimal quantity = rs.getObject("price_per_unit", BigDecimal.class);
        expenseTransactionDto.setExpenseCost(pricePerUnit.multiply(quantity));

        return expenseTransactionDto;
    }

    public List<ExpenseTransactionDto> getAllTransactionExpensesByMonth(){
        String query = """
                 SELECT * FROM transaction
                    INNER JOIN supply ON transaction.supply_id = supply.supply_id;
                """;

        List<ExpenseTransactionDto> transactionExpenses = jdbcTemplate
                .query(query, (rs, rowNum) -> mapExpenseTransactionResult(rs));

        return transactionExpenses;
    }

    private ExpenseDto mapExpenseResult(final ResultSet rs) throws SQLException {
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setExpenseId(rs.getLong("expense_id"));
        expenseDto.setExpenseCategoryName(rs.getString("expense_category_name"));
        expenseDto.setExpenseDescription(rs.getString("expense_description"));
        expenseDto.setExpenseDate(rs.getObject("expense_date", LocalDateTime.class));
        expenseDto.setExpenseCost(rs.getBigDecimal("expense_cost"));

        return expenseDto;
    }

    public List<ExpenseDto> getAllExpensesByMonth(){
        String query = """
                 SELECT * FROM expense
                    INNER JOIN expense_category ON expense.expense_category_id = expense_category.expense_category_id;
                """;
        List<ExpenseDto> expenses = jdbcTemplate
                .query(query, (rs, rowNum) -> mapExpenseResult(rs));

        return expenses;
    }

    private ExpenseBarGraphDto mapExpenseBarGraphDateResult(final ResultSet rs) throws SQLException {
        ExpenseBarGraphDto expenseBarGraphDto = new ExpenseBarGraphDto();
        expenseBarGraphDto.setExpenseMonth(rs.getString("expense_date"));
        expenseBarGraphDto.setMonthlyExpenses(rs.getDouble("expense"));
        expenseBarGraphDto.setMonthlyIncome(rs.getDouble("income"));
        return expenseBarGraphDto;
    }

    public List<ExpenseBarGraphDto> getGraphDataByMonth(FromToDate fromToDate){

        LocalDateTime fromDate = fromToDate.getFromDate();
        LocalDateTime toDate = fromToDate.getToDate();

        System.out.println("fromDate: " + fromDate);
        System.out.println("toDate: " + toDate);

        String query = """
                SELECT expense_date, SUM(expense_cost) as expense, income
                FROM (
                	SELECT DATE_FORMAT(transaction_date, '%M %Y') AS expense_date, SUM(price_per_unit * transaction_supply_quantity) AS expense_cost
                		FROM transaction WHERE transaction_type = 'STOCK_IN' AND transaction_date BETWEEN ? AND ?
                		GROUP BY expense_date
                 UNION
                	SELECT DATE_FORMAT(expense_date, '%M %Y') AS expense_date, SUM(expense_cost) AS expense_cost FROM expense
                	WHERE expense_date BETWEEN ? AND ?
                		GROUP BY expense_date
                ) AS expense
                 INNER JOIN(
                	SELECT DATE_FORMAT(order_time, '%M %Y') AS income_date, SUM(total_cost) AS income
                		FROM customer_food_order
                		INNER JOIN customer_order ON customer_food_order.order_id = customer_order.order_id
                		WHERE order_time BETWEEN ? AND ?
                		GROUP BY order_time) AS income ON expense_date = income_date
                GROUP BY expense_date;
                """;

        List<ExpenseBarGraphDto> graphData = jdbcTemplate.query(query, (rs, rowNum) -> mapExpenseBarGraphDateResult(rs), fromDate, toDate, fromDate, toDate, fromDate, toDate);

        return graphData;
    }

    public void addExpense(Long expenseCategoryId,
                           String expenseDescription,
                           LocalDateTime expenseDate,
                           BigDecimal expenseCost){

        String query = """
                INSERT INTO expense(expense_category_id, expense_description, expense_date, expense_cost)
                VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.update(query, expenseCategoryId, expenseDescription, expenseDate, expenseCost);
    }

    public void deleteExpense(Long expenseId){

        String query = """
                DELETE FROM expense WHERE expense_id = ?
                """;

        jdbcTemplate.update(query, expenseId);
    }

    private IncomeDto mapIncomeResult(final ResultSet rs) throws SQLException {
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setIncomeDate(rs.getString("income_date"));
        incomeDto.setDailyIncome(rs.getBigDecimal("daily_income"));

        return incomeDto;
    }

    public List<IncomeDto> getAllIncomeByMonth(){

        String query = """
                SELECT DATE_FORMAT(order_time, '%M %d, %Y') AS income_date, SUM(total_cost) AS daily_income
                FROM customer_food_order
                    INNER JOIN customer_order ON customer_food_order.order_id = customer_order.order_id
                GROUP BY order_time;
                """;

        List<IncomeDto> income = jdbcTemplate.query(query, (rs, rowNum) -> mapIncomeResult(rs));

        return income;
    }








}
