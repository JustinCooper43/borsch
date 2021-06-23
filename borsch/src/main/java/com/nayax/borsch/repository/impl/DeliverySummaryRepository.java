package com.nayax.borsch.repository.impl;

import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.order.OrderEntity;
import com.nayax.borsch.utility.OrderEntityHashNoAdditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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
                " [Order].ShawarmaTypeId dishId, ShawarmaType.[Name] dishName, ShawarmaType.Cost dishCost, " +
                " Addition.id additionId, Addition.[Name] additionName, Addition.Cost additionCost, " +
                " ExtraItem.id drinkId, ExtraItem.[Name] drinkName, ExtraItem.Cost drinkCost, " +
                " [Order].RemarkId remarkId, Remark.[Name] remarkName " +
                " FROM [Order] " +
                " JOIN ShawarmaType ON ShawarmaType.id = [Order].ShawarmaTypeId " +
                " JOIN AdditionSelectedOrder ON AdditionSelectedOrder.OrderId = [Order].id " +
                " JOIN Addition ON Addition.id = AdditionSelectedOrder.AdditionId " +
                " JOIN ExtraItem ON ExtraItem.id = [Order].ExtraItemId " +
                " JOIN Remark ON Remark.id = [Order].RemarkId " +
                " WHERE [Order].OrderSummaryId = ? " +
                " ORDER BY [Order].id ASC, Addition.id ; ";
        try {
            List<OrderEntityHashNoAdditions> rawList = jdbcTemplate.query(sql, new RowMapper<>() {
                @Override
                public OrderEntityHashNoAdditions mapRow(ResultSet rs, int rowNum) throws SQLException {
                    OrderEntityHashNoAdditions e = new OrderEntityHashNoAdditions();
                    e.setOrderId(rs.getLong("orderId"));
                    e.setQuantity(rs.getInt("quantity"));
                    e.setCut(rs.getBoolean("cut"));
//                e.setCreationTime(rs.getTimestamp("creationTime").toLocalDateTime());
//                e.setUserId(rs.getLong("userId"));
//                e.setOrderSummaryId(rs.getLong("orderSummaryId"));
                    GeneralPriceItemEntity dish = new GeneralPriceItemEntity();
                    dish.setName(rs.getNString("dishName"));
                    dish.setPrice(rs.getBigDecimal("dishCost"));
                    dish.setId(rs.getLong("dishId"));
//                dish.setActive(rs.getString("dishActive"));
                    e.setDish(dish);
                    GeneralPriceItemEntity extraItem = new GeneralPriceItemEntity();
                    extraItem.setName(rs.getNString("drinkName"));
                    extraItem.setPrice(rs.getBigDecimal("drinkCost"));
                    extraItem.setId(rs.getLong("drinkId"));
//                extraItem.setActive(rs.getString("extraItemActive"));
                    e.setDrink(extraItem);
                    GeneralPriceItemEntity remark = new GeneralPriceItemEntity();
                    remark.setName(rs.getNString("remarkName"));
                    remark.setId(rs.getLong("remarkId"));
//                remark.setActive(rs.getString("remarkActive"));
                    e.setRemark(remark);
                    GeneralPriceItemEntity addition = new GeneralPriceItemEntity();
                    addition.setName(rs.getNString("additionName"));
                    addition.setPrice(rs.getBigDecimal("additionCost"));
                    addition.setId(rs.getLong("additionId"));
//                addition.setActive(rs.getString("additionActive"));
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
                results.add(e);
            }
            return results;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
