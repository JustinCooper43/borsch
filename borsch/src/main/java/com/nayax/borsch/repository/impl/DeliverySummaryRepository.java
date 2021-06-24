package com.nayax.borsch.repository.impl;

import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import com.nayax.borsch.model.entity.order.OrderEntity;
import com.nayax.borsch.model.entity.order.OrderSumTimerEntity;
import com.nayax.borsch.model.entity.user.CashierEntity;
import com.nayax.borsch.utility.OrderEntityHashNoAdditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DeliverySummaryRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<OrderEntity> getByOrderSummaryId(Long orderSummaryId) {
        String sql = " SELECT [Order].id orderId, [Order].Quantity quantity, [Order].CutInHalf cut, " +
                " [Order].ShawarmaTypeId dishId, [Order].CreationTime creationTime, ShawarmaType.[Name] dishName, " +
                " ShawarmaType.Cost dishCost, Addition.id additionId, " +
                "Addition.[Name] additionName, Addition.Cost additionCost, " +
                " ExtraItem.id drinkId, ExtraItem.[Name] drinkName, ExtraItem.Cost drinkCost, " +
                " [Order].RemarkId remarkId, Remark.[Name] remarkName " +
                " FROM [Order] " +
                " JOIN ShawarmaType ON ShawarmaType.id = [Order].ShawarmaTypeId " +
                " JOIN AdditionSelectedOrder ON AdditionSelectedOrder.OrderId = [Order].id " +
                " JOIN Addition ON Addition.id = AdditionSelectedOrder.AdditionId " +
                " JOIN ExtraItem ON ExtraItem.id = [Order].ExtraItemId " +
                " JOIN Remark ON Remark.id = [Order].RemarkId " +
                " WHERE [Order].OrderSummaryId = ? ; ";
        try {
            List<OrderEntityHashNoAdditions> rawList = jdbcTemplate.query(sql, new RowMapper<>() {
                @Override
                public OrderEntityHashNoAdditions mapRow(ResultSet rs, int rowNum) throws SQLException {
                    OrderEntityHashNoAdditions e = new OrderEntityHashNoAdditions();
                    e.setOrderId(rs.getLong("orderId"));
                    e.setQuantity(rs.getInt("quantity"));
                    e.setCut(rs.getBoolean("cut"));
                    e.setCreationTime(rs.getTimestamp("creationTime").toLocalDateTime());
                    ShawarmaItemEntity dish = new ShawarmaItemEntity();
                    dish.setName(rs.getNString("dishName"));
                    dish.setPrice(rs.getBigDecimal("dishCost"));
                    dish.setId(rs.getLong("dishId"));
                    e.setDish(dish);
                    GeneralPriceItemEntity extraItem = new GeneralPriceItemEntity();
                    extraItem.setName(rs.getNString("drinkName"));
                    extraItem.setPrice(rs.getBigDecimal("drinkCost"));
                    extraItem.setId(rs.getLong("drinkId"));
                    e.setDrink(extraItem);
                    GeneralPriceItemEntity remark = new GeneralPriceItemEntity();
                    remark.setName(rs.getNString("remarkName"));
                    remark.setId(rs.getLong("remarkId"));
                    e.setRemark(remark);
                    GeneralPriceItemEntity addition = new GeneralPriceItemEntity();
                    addition.setName(rs.getNString("additionName"));
                    addition.setPrice(rs.getBigDecimal("additionCost"));
                    addition.setId(rs.getLong("additionId"));
                    e.setAdditions(List.of(addition));
                    return e;
                }
            }, orderSummaryId);
            Map<OrderEntityHashNoAdditions, List<GeneralPriceItemEntity>> orderToAdditions = new HashMap<>();
            for (OrderEntityHashNoAdditions e : rawList) {
                orderToAdditions.putIfAbsent(e, new ArrayList<>());
                orderToAdditions.get(e).add(e.getAdditions().get(0));
            }
            List<OrderEntity> results = new ArrayList<>();
            for (OrderEntityHashNoAdditions e : orderToAdditions.keySet()) {
                e.setAdditions(orderToAdditions.get(e));
                results.add((OrderEntity) e);
            }
            return results;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public OrderSumTimerEntity getTimerBeforeDate(LocalDateTime date) {
        String sql = " SELECT TOP (1) OrderSummary.id, StartTime, StopTime, EndTime, CashierId, " +
                " Cashier.CashPaymentAllowed, Cashier.CCNumber, Cashier.CCBank, " +
                " Cashier.CCNote, Cashier.CCQRCode " +
                " FROM OrderSummary " +
                " JOIN Cashier ON Cashier.id = CashierId " +
                " WHERE StartTime < ? " +
                " ORDER BY StartTime DESC ; ";
        try {
            return jdbcTemplate.queryForObject(sql, new RowMapper<>() {
                @Override
                public OrderSumTimerEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    OrderSumTimerEntity e = new OrderSumTimerEntity();
                    CashierEntity c = new CashierEntity();
                    c.setCashierId(rs.getLong("CashierId"));
                    c.setCashPaymentAllowed(rs.getBoolean("CashPaymentAllowed"));
                    c.setCardNumber(rs.getNString("CCNumber"));
                    c.setCardBank(rs.getNString("CCBank"));
                    c.setCardNote(rs.getNString("CCNote"));
                    c.setCardQrCode(rs.getNString("CCQRCode"));
                    e.setCashier(c);
                    e.setId(rs.getLong("id"));
                    Timestamp startTime = rs.getTimestamp("StartTime");
                    if (startTime != null) {
                        e.setStartTime(startTime.toLocalDateTime());
                    }
                    Timestamp stopTime = rs.getTimestamp("StopTime");
                    if (stopTime != null) {
                        e.setStopTime(stopTime.toLocalDateTime());
                    }
                    Timestamp endTime = rs.getTimestamp("EndTime");
                    if (endTime != null) {
                        e.setFinishTime(endTime.toLocalDateTime());
                    }
                    return e;
                }
            }, date);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
