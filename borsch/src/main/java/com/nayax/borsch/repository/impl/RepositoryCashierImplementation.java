package com.nayax.borsch.repository.impl;


import com.nayax.borsch.model.entity.user.CashierEntity;

import com.nayax.borsch.model.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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

        CashierEntity cashierEntity = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CashierEntity.class),
                entity.getCashierId(), entity.isCashPaymentAllowed(), entity.getCardNumber(), entity.getCardBank(), entity.getCardNote(),
                entity.getCardQrCode());


        return cashierEntity;
    }


    public CashierEntity update(CashierEntity entity) {
        String sql = "UPDATE Cashier SET " +
                "[Cashier].CashPaymentAllowed =?, [Cashier].CCNumber =?, [Cashier].CCBank =?, " +
                "[Cashier].CCNote =?, [Cashier].CCQRCode =? where [Cashier].UserId =?";

//        int result = 0;

        CashierEntity result = new CashierEntity();
        try {
            result = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CashierEntity.class),
                    entity.isCashPaymentAllowed(), entity.getCardNumber(), entity.getCardBank(), entity.getCardNote(),
                    entity.getCardQrCode(), entity.getCashierId());
        } catch (EmptyResultDataAccessException e) {
            return result;
        }
        return result;
    }

    public Optional<CashierEntity> findById(Long id) {

        String sql = "SELECT [Cashier].UserId, [Cashier].CashPaymentAllowed " +
                ",[Cashier].CCNumber, [Cashier].CCBank, [Cashier].CCNote, [Cashier].CCQRCode " +
                "FROM CASHIER where [Cashier].UserId =?";

        Optional<CashierEntity> result = Optional.empty();
        try {
            CashierEntity cashierEntity = jdbcTemplate.queryForObject(sql, new RowMapper<CashierEntity>() {

                @Override
                public CashierEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    CashierEntity cashier = new CashierEntity();
                    cashier.setCashierId((Long) rs.getObject("UserId"));
                    cashier.setCashPaymentAllowed(rs.getBoolean("CashPaymentAllowed"));
                    cashier.setCardNumber(rs.getNString("CCNumber"));
                    cashier.setCardBank(rs.getNString("CCBank"));
                    cashier.setCardNote(rs.getNString("CCNote"));
                    cashier.setCardQrCode(rs.getNString("CCQRCode"));

                    return cashier;
                }
            }, id);
            result = Optional.ofNullable(cashierEntity);
        } catch (EmptyResultDataAccessException e) {
            System.err.printf("User %s not found ", id == null ? null : id.toString());
        }

        return result;
    }
}


