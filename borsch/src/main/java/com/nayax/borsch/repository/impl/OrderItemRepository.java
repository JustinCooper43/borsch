package com.nayax.borsch.repository.impl;

import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import com.nayax.borsch.model.entity.order.OrderEntity;
import com.nayax.borsch.model.entity.payment.PaymentConfirmation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderItemRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public OrderEntity add(OrderEntity entity) {
        String insOrder = "INSERT INTO [Order] " +
                " (UserId, CreationTime, ShawarmaTypeId, ExtraItemId, RemarkId, CutInHalf, Quantity, OrderSummaryId) " +
                //    1        2               3            4            5           6         7
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insOrder, new String[]{"id"});
                ps.setObject(1, entity.getUserId(), Types.BIGINT);
                ps.setObject(2, entity.getCreationTime(), Types.TIMESTAMP);
                ps.setObject(3, entity.getDish().getId(), Types.BIGINT);
                ps.setObject(4, entity.getDrink().getId(), Types.BIGINT);
                ps.setObject(5, entity.getRemark().getId(), Types.BIGINT);
                ps.setObject(6, entity.isCut() ? 1 : 0, Types.BIT);
                ps.setObject(7, entity.getQuantity(), Types.SMALLINT);
                ps.setObject(8, entity.getOrderSummaryId(), Types.BIGINT);
                return ps;
            }
        }, keyHolder);
        Long insertedOrderId = keyHolder.getKey().longValue();
        String insConnections = " INSERT INTO [AdditionSelectedOrder] (OrderId, AdditionId) VALUES (?, ?) ";
        jdbcTemplate.batchUpdate(insConnections, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                GeneralPriceItemEntity addition = entity.getAdditions().get(i);
                ps.setObject(1, insertedOrderId, Types.BIGINT);
                ps.setObject(2, addition.getId(), Types.BIGINT);
            }

            @Override
            public int getBatchSize() {
                return entity.getAdditions().size();
            }
        });
        return findById(insertedOrderId).get();
    }

    public Optional<OrderEntity> findById(Long id) {
        String sql = " SELECT [Order].[id] orderId, [Order].[CreationTime] creationTime, [Order].[UserId] userId, " +
                " [Order].[CutInHalf] cut, [Order].[OrderSummaryId] orderSummaryId, [Order].[Quantity] quantity, " +
                " ShawarmaType.[Name] dishName, ShawarmaType.Cost dishCost, ShawarmaType.Active dishActive, ShawarmaType.id dishId, " +
                " ExtraItem.[Name] extraItemName, ExtraItem.Cost extraItemCost, ExtraItem.Active extraItemActive, ExtraItem.id extraItemId, " +
                " Remark.[Name] remarkName, Remark.Active remarkActive, Remark.id remarkId, " +
                " Addition.[Name] additionName, Addition.Cost additionCost, Addition.Active additionActive, Addition.id additionId " +
                " FROM [Order] " +
                " JOIN [ShawarmaType] ON ShawarmaType.id = ShawarmaTypeId " +
                " LEFT JOIN [ExtraItem] ON ExtraItem.id = ExtraItemId " +
                " LEFT JOIN [Remark] ON Remark.id = RemarkId " +
                " LEFT JOIN [AdditionSelectedOrder] ON AdditionSelectedOrder.OrderId = [Order].id " +
                " LEFT JOIN [Addition] ON Addition.id = AdditionSelectedOrder.AdditionId " +
                " WHERE [Order].id = ? ; ";
        try {
            OrderEntity entity = jdbcTemplate.query(sql, new ResultSetExtractor<>() {
                @Override
                public OrderEntity extractData(ResultSet rs) throws SQLException {
                    rs.next();
                    OrderEntity e = new OrderEntity();
                    e.setOrderId(rs.getLong("orderId"));
                    e.setCreationTime(rs.getTimestamp("creationTime").toLocalDateTime());
                    e.setUserId(rs.getLong("userId"));
                    //e.setCut(rs.getInt("cut")>0);
                    e.setCut(rs.getBoolean("cut"));
                    e.setOrderSummaryId(rs.getLong("orderSummaryId"));
                    e.setQuantity(rs.getInt("quantity"));
                    ShawarmaItemEntity dish = new ShawarmaItemEntity();
                    dish.setName(rs.getNString("dishName"));
                    dish.setPrice(rs.getBigDecimal("dishCost"));
                    dish.setActive(rs.getString("dishActive"));
                    dish.setId(rs.getLong("dishId"));
                    e.setDish(dish);
                    GeneralPriceItemEntity extraItem = new GeneralPriceItemEntity();
                    extraItem.setName(rs.getNString("extraItemName"));
                    extraItem.setPrice(rs.getBigDecimal("extraItemCost"));
                    extraItem.setActive(rs.getString("extraItemActive"));
                    extraItem.setId(rs.getLong("extraItemId"));
                    e.setDrink(extraItem);
                    GeneralPriceItemEntity remark = new GeneralPriceItemEntity();
                    remark.setName(rs.getNString("remarkName"));
                    remark.setPrice(null);
                    remark.setActive(rs.getString("remarkActive"));
                    remark.setId(rs.getLong("remarkId"));
                    e.setRemark(remark);
                    List<GeneralPriceItemEntity> additions = new ArrayList<>();
                    do {
                        GeneralPriceItemEntity addition = new GeneralPriceItemEntity();
                        addition.setName(rs.getNString("additionName"));
                        addition.setPrice(rs.getBigDecimal("additionCost"));
                        addition.setActive(rs.getString("additionActive"));
                        addition.setId(rs.getLong("additionId"));
                        additions.add(addition);
                    } while (rs.next());
                    e.setAdditions(additions);
                    return e;
                }
            }, id);
            return Optional.ofNullable(entity);
        } catch (DataAccessException dataAccessException) {

        }
        return Optional.empty();
    }

    public Optional<List<Long>> getOrderIdByUser(PaymentConfirmation entity) {
        String sql = " SELECT TOP (1) [Order].id, s.CashierId " +
                " FROM [Order] " +
                " JOIN [OrderSummary] s ON s.id = [Order].OrderSummaryId " +
                " WHERE [Order].UserId = ? " +
                " AND s.StartTime BETWEEN ? AND ? ; ";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new RowMapper<>() {
            @Override
            public List<Long> mapRow(ResultSet rs, int rowNum) throws SQLException {
                List<Long> orderWithCashier = new ArrayList<>(2);
                orderWithCashier.add(rs.getLong("id"));
                orderWithCashier.add(rs.getLong("CashierId"));
                return orderWithCashier;
            }
        }, entity.getUserId(), entity.getOrderDate(), entity.getOrderDate().plusDays(1)));
    }

    public OrderEntity deleteById(Long id) {
        OrderEntity toDelete = findById(id).orElse(null);
        if (toDelete != null) {
            String sql = "DELETE FROM AdditionSelectedOrder WHERE OrderId = ? " +
                    "DELETE FROM [Order] WHERE [Order].id = ?  ";
            jdbcTemplate.update(sql, id, id);
        }
        return toDelete;
    }
}

