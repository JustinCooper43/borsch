package com.nayax.borsch.repository.impl;

import com.nayax.borsch.model.entity.payment.PaymentConfirmation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PaymentRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean confirmPayment(PaymentConfirmation entity) {
        String sql = " INSERT INTO Payment ([Sum] ,[Completion] ,[Confirmation] ,[Type] ,[CashierId] ,[OrderId]) " +
                " VALUES (?, 1, 1, 0, ?, ?) ; ";
        return 1 == jdbcTemplate.update(sql, entity.getPaid(), entity.getCashierId(), entity.getOrderId());
    }

    public Optional<BigDecimal> getPayedSumForLatestOrderSummary(Long userId) {
        String sql = " SELECT TOP (1) SUM (Payment.[sum]) totalPayed " +
                " FROM [Order] JOIN Payment on Payment.OrderId = [Order].id " +
                " JOIN OrderSummary on [Order].OrderSummaryId = OrderSummary.id " +
                " WHERE [Order].UserId = ? AND Payment.Completion = 1 AND Payment.Confirmation = 1 " +
                " GROUP BY OrderSummary.StartTime " +
                " ORDER BY OrderSummary.StartTime DESC ; ";
        List<BigDecimal> results = jdbcTemplate.query(sql, new RowMapper<>() {
            @Override
            public BigDecimal mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getBigDecimal("totalPayed");
            }
        }, userId);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public Optional<BigDecimal> getTotalCostForLatestOrderSummary(Long userId) {
        String sql = " SELECT TOP (1) SUM([ShawarmaType].Cost*[Order].Quantity) " +
                " + SUM([ExtraItem].Cost*[Order].Quantity) " +
                " + SUM([Addition].Cost*[Order].Quantity) totalCost " +
                " FROM [Order] " +
                " JOIN [OrderSummary] ON [OrderSummary].id = [Order].OrderSummaryId " +
                " JOIN [ShawarmaType] ON [ShawarmaType].id = [Order].ShawarmaTypeId " +
                " LEFT JOIN [ExtraItem] ON [ExtraItem].id = [Order].ExtraItemId " +
                " LEFT JOIN [AdditionSelectedOrder] ON [AdditionSelectedOrder].OrderId = [Order].id " +
                " LEFT JOIN [Addition] ON [Addition].id = [AdditionSelectedOrder].AdditionId " +
                " WHERE [Order].UserId = ? " +
                " GROUP BY OrderSummary.StartTime " +
                " ORDER BY OrderSummary.StartTime DESC ; ";
        List<BigDecimal> results = jdbcTemplate.query(sql, new RowMapper<>() {
            @Override
            public BigDecimal mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getBigDecimal("totalCost");
            }
        }, userId);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public Map<Long, BigDecimal> getSumPaymentByUser(LocalDate date) {
        LocalDateTime localDateTime = date.atStartOfDay();
        String sql =
                "declare @datee datetime = ?;\n" +
                        "select Sum(p.[Sum]) paySum, o.UserId userId from Payment p\n" +
                        "join [Order] o on o.id = p.OrderId\n" +
                        "where (@datee is Null) or o.CreationTime between @datee and DATEADD(day,1,@datee)\n" +
                        "group by  o.UserId  ";
        Map<Long, BigDecimal> pay = new HashMap<>();
        jdbcTemplate.query(sql, new RowMapper<BigDecimal>() {
            @Override
            public BigDecimal mapRow(ResultSet rs, int rowNum) throws SQLException {
                pay.put((Long) rs.getObject("userId"), rs.getBigDecimal("paySum"));
                return null;
            }
        }, localDateTime);
        return pay;
    }
}

