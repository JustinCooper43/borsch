package com.nayax.borsch.repository.impl;

import com.nayax.borsch.model.entity.assortment.AssortmentRespEntity;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import com.nayax.borsch.model.entity.order.OrderEntity;
import com.nayax.borsch.model.entity.order.OrderSummaryEntity;
import com.nayax.borsch.model.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class RepositoryOrderSummaryImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<OrderSummaryEntity> findAll(LocalDate date) {
        LocalDateTime localDateTime = date.atStartOfDay();

        String sql =
                "declare @datee datetime = ?;\n" +
                        "Select o.id orderId,o.CreationTime orderTime, o.CutInHalf, o.Quantity,sh.id shawId,sh.[Name] shawName," +
                        "sh.Active shawAct,sh.Cost shawCost,sh.Halfable shawHalf,\n" +
                        "rem.id remId,rem.[Name] remName, rem.Active remAct, \n" +
                        "extra.id extraId, extra.[Name] extraName, extra.Cost extraCost, extra.Active extraAct,\n" +
                        "o.CutInHalf orderCut,o.Quantity orderQuantity,o.UserId userId,\n" +
                        "u.Active userAct,u.Email userEmail,u.FirstName userFName,u.LastName userLName,u.PhoneNumber userPhone\n" +
                        "from [Order] o \n" +
                        "left join [User] u on u.id = o.UserId and u.Active = 'Y'\n" +
                        "join ShawarmaType sh on sh.id = o.ShawarmaTypeId\n" +
                        "join Remark rem on rem.id = o.RemarkId\n" +
                        "join ExtraItem extra on extra.id = o.ExtraItemId\n" +
                        "where o.CreationTime between @datee and DATEADD(day,1,@datee) " +
                        "order by userId";

        Map<UserEntity, List<OrderEntity>> orderByUserMap = new HashMap<>();
        Set<Long> orderIds = new HashSet<>();
        List<OrderSummaryEntity> summaryEntities = new ArrayList<>();
        jdbcTemplate.query(sql, (RowMapper<OrderSummaryEntity>) (rs, rowNum) -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setId((Long) rs.getObject("userId"));
            userEntity.setFirstName((String) rs.getObject("userFName"));
            userEntity.setLastName((String) rs.getObject("userLName"));
            userEntity.seteMail((String) rs.getObject("userEmail"));
            userEntity.setPhone((String) rs.getObject("userPhone"));
            userEntity.setActive(rs.getString("userAct"));

            orderByUserMap.putIfAbsent(userEntity, new ArrayList<>());
            ShawarmaItemEntity shawarmaEntity = new ShawarmaItemEntity();
            shawarmaEntity.setId((Long) rs.getObject("shawId"));
            shawarmaEntity.setName((String) rs.getObject("shawName"));
            shawarmaEntity.setPrice((BigDecimal) rs.getObject("shawCost"));
            shawarmaEntity.setActive(rs.getString("shawAct"));

            GeneralPriceItemEntity remark = new GeneralPriceItemEntity();
            remark.setId((Long) rs.getObject("remId"));
            remark.setName((String) rs.getObject("remName"));
            remark.setActive(rs.getString("remAct"));

            GeneralPriceItemEntity drink = new GeneralPriceItemEntity();
            drink.setId((Long) rs.getObject("extraId"));
            drink.setName((String) rs.getObject("extraName"));
            drink.setPrice((BigDecimal) rs.getObject("extraCost"));
            drink.setActive(rs.getString("extraAct"));

            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setOrderId((Long) rs.getObject("orderId"));
            orderEntity.setDish(shawarmaEntity);
            orderEntity.setCut(rs.getInt("CutInHalf") > 0);
            orderEntity.setQuantity((int) rs.getShort("Quantity"));
            orderEntity.setRemark(remark);
            orderEntity.setDrink(drink);
            orderEntity.setCreationTime(rs.getTimestamp("orderTime").toLocalDateTime());

            orderIds.add(orderEntity.getOrderId());
            orderByUserMap.get(userEntity).add(orderEntity);
            return null;
        }, localDateTime);

        if (orderIds.size() != 0) {
            Map<Long, List<GeneralPriceItemEntity>> additions = findAllAdditions(orderIds);

            for (UserEntity var : orderByUserMap.keySet()) {
                for (OrderEntity entity : orderByUserMap.get(var)) {
                    entity.setAdditions(additions.get(entity.getOrderId()));
                }
            }

            for (UserEntity var : orderByUserMap.keySet()) {
                OrderSummaryEntity orderSummaryEntity = new OrderSummaryEntity();
                orderSummaryEntity.setUser(var);
                orderSummaryEntity.setOrders(orderByUserMap.get(var));
                orderSummaryEntity.setOrderDate(localDateTime.toLocalDate());
                summaryEntities.add(orderSummaryEntity);
            }
        }
        return summaryEntities;
    }

    public Map<Long, List<GeneralPriceItemEntity>> findAllAdditions(Set<Long> ids) {

        String sqlAddition =
                "Select a.id adId, a.[Name] adName, a.Cost adCost, ad.OrderId adOrder from Addition a\n" +
                        "join AdditionSelectedOrder ad on ad.AdditionId = a.id\n" +
                        "where ad.OrderId in (:ids)";
        Map<Long, List<GeneralPriceItemEntity>> addition = new HashMap<>();
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        namedParameterJdbcTemplate.query(sqlAddition, parameters, (RowMapper<AssortmentRespEntity>) (rs, rowNum) -> {

            addition.putIfAbsent((Long) rs.getObject("adOrder"), new ArrayList<>());
            GeneralPriceItemEntity entity = new GeneralPriceItemEntity();
            entity.setId((Long) rs.getObject("adId"));
            entity.setName((String) rs.getObject("adName"));
            entity.setPrice((BigDecimal) rs.getObject("adCost"));
            addition.get((Long) rs.getObject("adOrder")).add(entity);
            return null;
        });
        return addition;
    }

    public Optional<Long> getLatestOrderSummaryId() {
        String sql = " SELECT TOP (1) OrderSummary.id id " +
                " FROM  OrderSummary " +
                " WHERE StopTime IS NULL " +
                " ORDER BY OrderSummary.StartTime DESC ; ";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                return (Long) rs.getObject("id");
            }
        }));
    }

    public boolean startNewSummaryOrder(entity) {
        String sql = " INSERT INTO OrderSummary (CashierId, StartTime, EndTime) " +
                " SELECT [Cashier].id, ?, ? " +
                " FROM [Cashier] " +
                " WHERE [Cashier].UserId = ? ; ";
        return 1 == jdbcTemplate.update(sql, entity.getUserId(), entity.getStartTime(), entity.getEndTime());
    }

    public LocalDateTime getSummaryOrderStatus(Long orderSumId) {
        String sql = " SELECT EndTime " +
                " FROM [OrderSummary] " +
                " WHERE StopTime IS NULL AND id = ? ; ";
        return jdbcTemplate.queryForObject(sql, new RowMapper<>() {
            @Override
            public LocalDateTime mapRow(ResultSet rs, int rowNum) throws SQLException {
                Timestamp endTime = rs.getTimestamp("EndTime");
                if (endTime != null) {
                    return endTime.toLocalDateTime();
                }
                return null;
            }
        }, orderSumId);
    }

    public boolean stopSummaryOrder(LocalDateTime stopTime) {
        String sql = " UPDATE OrderSummary SET StopTime = ? " +
                " WHERE StopTime IS NULL ";
        return 1 == jdbcTemplate.update(sql, stopTime);
    }
}
