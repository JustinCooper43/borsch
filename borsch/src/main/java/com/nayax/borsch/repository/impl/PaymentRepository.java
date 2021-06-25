package com.nayax.borsch.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PaymentRepository {


    @Autowired
    private JdbcTemplate jdbcTemplate;



    public Map<Long, BigDecimal> getSumPaymentByUser(LocalDate date){
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
