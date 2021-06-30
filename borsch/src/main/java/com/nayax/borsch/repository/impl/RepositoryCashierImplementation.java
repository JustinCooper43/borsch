package com.nayax.borsch.repository.impl;


import com.nayax.borsch.model.entity.user.CashierEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Repository
public class RepositoryCashierImplementation {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public CashierEntity add(CashierEntity entity) {
        String sql = "INSERT INTO Cashier" +
                "([Cashier].UserId, [Cashier].CashPaymentAllowed " +
                ",[Cashier].CCNumber, [Cashier].CCBank, [Cashier].CCNote, [Cashier].CCQRCode)" +
                " OUTPUT INSERTED.* VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CashierEntity.class),
                entity.getUserId(), entity.isCashPaymentAllowed(), entity.getCardNumber(), entity.getCardBank(), entity.getCardNote(),
                entity.getCardQrCode());
    }

    public CashierEntity update(CashierEntity entity) {
        String sql = "UPDATE Cashier SET " +
                "[Cashier].CashPaymentAllowed =?, [Cashier].CCNumber =?, [Cashier].CCBank =?, " +
                "[Cashier].CCNote =?, [Cashier].CCQRCode =? where [Cashier].UserId =? AND [Cashier].id = ?";
        int result = 0;
        try {
            result = jdbcTemplate.update(sql, entity.isCashPaymentAllowed(), entity.getCardNumber(),
                    entity.getCardBank(), entity.getCardNote(),
                    entity.getCardQrCode(), entity.getUserId(), entity.getId());
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return 1 == result ? findById(entity.getUserId()).get() : null;
    }

    public Optional<CashierEntity> findById(Long id) {
        String sql = "SELECT [Cashier].id, [Cashier].UserId, [Cashier].CashPaymentAllowed " +
                ",[Cashier].CCNumber, [Cashier].CCBank, [Cashier].CCNote, [Cashier].CCQRCode " +
                "FROM CASHIER where [Cashier].UserId =?";
        try {
            List<CashierEntity> cashierEntity = jdbcTemplate.query(sql, new RowMapper<CashierEntity>() {
                @Override
                public CashierEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    CashierEntity cashier = new CashierEntity();
                    cashier.setId((Long) rs.getObject("id"));
                    cashier.setUserId((Long) rs.getObject("UserId"));
                    cashier.setCashPaymentAllowed(rs.getBoolean("CashPaymentAllowed"));
                    cashier.setCardNumber(rs.getNString("CCNumber"));
                    cashier.setCardBank(rs.getNString("CCBank"));
                    cashier.setCardNote(rs.getNString("CCNote"));
                    cashier.setCardQrCode(rs.getNString("CCQRCode"));
                    return cashier;
                }
            }, id);
            return 1 == cashierEntity.size() ? Optional.ofNullable(cashierEntity.get(0)) : Optional.empty();
        } catch (EmptyResultDataAccessException e) {
            System.err.printf("User %s not found ", id == null ? null : id.toString());
        }
        return Optional.empty();
    }
}


