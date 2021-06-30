package com.nayax.borsch.repository.impl;

import com.nayax.borsch.model.entity.PageEntity;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import com.nayax.borsch.model.entity.order.OrderEntity;
import com.nayax.borsch.model.entity.payment.OrderItemTotalCostInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class OrderItemRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<OrderEntity> getListOrders(Long userId, LocalDate dateTime) {
        LocalDateTime localDateTime = dateTime.atStartOfDay();
        String sql =
                " declare @datee datetime = ?;" +
                        " SELECT [Order].UserId userId , [Order].CreationTime creatTime , [Order].id orderId, [Order].Quantity quantity, [Order].CutInHalf cut,\n" +
                        "  [Order].OrderSummaryId sumId,\n" +
                        "  [Order].ShawarmaTypeId [dish.id], ShawarmaType.[Name] [dish.name], ShawarmaType.Cost [dish.price], ShawarmaType.Active actShaw , ShawarmaType.Halfable shawHalv, \n" +
                        "  ExtraItem.id [drink.id], ExtraItem.[Name] [drink.name], ExtraItem.Cost [drink.price], ExtraItem.Active actExtr , \n" +
                        "  [Order].RemarkId [remark.id], Remark.[Name] [remark.name],  Remark.Active actRem  \n" +
                        "  FROM [Order] \n" +
                        "  JOIN ShawarmaType ON ShawarmaType.id = [Order].ShawarmaTypeId  \n" +
                        "  JOIN ExtraItem ON ExtraItem.id = [Order].ExtraItemId \n" +
                        "  JOIN Remark ON Remark.id = [Order].RemarkId  \n" +
                        "  WHERE [Order].UserId = ? AND [Order].CreationTime  between @datee and  DATEADD(day,1,@datee)";

        List<OrderEntity> listOrders = new ArrayList<>();
        listOrders = jdbcTemplate.query(sql, new RowMapper<OrderEntity>() {
            @Override
            public OrderEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                ShawarmaItemEntity dish = new ShawarmaItemEntity();
                dish.setId((Long) rs.getObject("dish.id"));
                dish.setName(rs.getNString("dish.name"));
                dish.setPrice((BigDecimal) rs.getObject("dish.price"));
                dish.setActive(rs.getString("actShaw"));
                dish.setHalfAble(rs.getBoolean("shawHalv"));

                GeneralPriceItemEntity drink = new GeneralPriceItemEntity();
                drink.setId((Long) rs.getObject("drink.id"));
                drink.setName(rs.getNString("drink.name"));
                drink.setPrice((BigDecimal) rs.getObject("drink.price"));
                drink.setActive(rs.getString("actExtr"));

                GeneralPriceItemEntity remark = new GeneralPriceItemEntity();
                remark.setId((Long) rs.getObject("remark.id"));
                remark.setName(rs.getNString("remark.name"));
                remark.setActive(rs.getString("actRem"));

                OrderEntity entity = new OrderEntity();
                entity.setOrderId((Long) rs.getObject("orderId"));
                entity.setDish(dish);
                entity.setDrink(drink);
                entity.setRemark(remark);
                entity.setCut(rs.getBoolean("cut"));
                entity.setQuantity((int) rs.getShort("quantity"));
                entity.setUserId((Long) rs.getObject("userId"));
                Timestamp creatTime = rs.getTimestamp("creatTime");
                if (creatTime != null) {
                    entity.setCreationTime(creatTime.toLocalDateTime());
                }
                entity.setOrderSummaryId((Long) rs.getObject("sumId"));

                Long orderId = (Long) rs.getObject("orderId");
                return entity;
            }
        }, localDateTime,userId);

        return listOrders;
    }

    public Map<Long, List<GeneralPriceItemEntity>> getMapAdditions(Set<Long> setOrderId, LocalDate dateTime) {
        LocalDateTime localDateTime = dateTime.atStartOfDay();
        Map<Long, List<GeneralPriceItemEntity>> mapAddition = new HashMap<>();

        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("setOrderId", setOrderId);
        parameter.addValue("dateTime", localDateTime);

        String sql = "declare @datee datetime = :dateTime;" +
                " SELECT [Order].id orderId, Addition.id [addition.id], Addition.[Name] [addition.name], " +
                " Addition.Cost [addition.price],  Addition.Active actAdd " +
                " FROM [Order]  " +
                " JOIN AdditionSelectedOrder ON AdditionSelectedOrder.OrderId = [Order].id  " +
                " JOIN Addition ON Addition.id = AdditionSelectedOrder.AdditionId  " +
                " WHERE [Order].id IN (:setOrderId) AND [Order].CreationTime  between @datee and  DATEADD(day,1,@datee) " +
                " ORDER BY [Order].id DESC";

        namedParameterJdbcTemplate.query(sql, parameter, new RowMapper<GeneralPriceItemEntity>() {
                    @Override
                    public GeneralPriceItemEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                        GeneralPriceItemEntity addition = new GeneralPriceItemEntity();
                        addition.setId((Long) rs.getObject("addition.id"));
                        addition.setPrice((BigDecimal) rs.getObject("addition.price"));
                        addition.setName(rs.getNString("addition.name"));
                        addition.setActive(rs.getString("actAdd"));

                        Long orderId = (Long) rs.getObject("orderId");
                        mapAddition.putIfAbsent(orderId, new ArrayList<>());
                        mapAddition.get(orderId).add(addition);
                        return null;
                    }
                }
        );
        return mapAddition;
    }


    public List<OrderItemTotalCostInfo> getOrderInfo(LocalDateTime dateTime) {

        String sql = " SELECT [Order].UserId userId ,  [Order].CreationTime creatTime , [Order].id orderId, [Order].Quantity quantity,  " +
                " ShawarmaType.Cost [dish.price],  " +
                " ExtraItem.Cost [drink.price], " +
                " sum(Addition.[Cost]) additionCost, " +
                " sum(Payment.[Sum]) totalPayed  " +
                " FROM [Order]  " +
                " JOIN ShawarmaType ON ShawarmaType.id = [Order].ShawarmaTypeId   " +
                " JOIN ExtraItem ON ExtraItem.id = [Order].ExtraItemId   " +
                " JOIN AdditionSelectedOrder ON AdditionSelectedOrder.OrderId = [Order].id  " +
                " JOIN Addition ON Addition.id = AdditionSelectedOrder.AdditionId  " +
                " JOIN Payment ON Payment.OrderId = [Order].id " +
                " WHERE  [Order].CreationTime = ? " +
                " AND Payment.Confirmation = 1 " +
                " GROUP BY [Order].UserId, [Order].CreationTime, [Order].id , [Order].Quantity, ShawarmaType.Cost, ExtraItem.Cost ";

        List<OrderItemTotalCostInfo> listResult;

        listResult = jdbcTemplate.query(sql, new RowMapper<OrderItemTotalCostInfo>() {
            @Override
            public OrderItemTotalCostInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                OrderItemTotalCostInfo entity = new OrderItemTotalCostInfo();
                entity.setUserId((Long) rs.getObject("userId"));
                entity.setOrderId((Long) rs.getObject("orderId"));
                entity.setDrinkCost((BigDecimal) rs.getObject("drink.price"));
                entity.setDishCost((BigDecimal) rs.getObject("dish.price"));
                entity.setAdditionCost((BigDecimal) rs.getObject("additionCost"));
                entity.setPayedSum((BigDecimal) rs.getObject("totalPayed"));
                entity.setQuantity((int) rs.getShort("quantity"));

                return entity;
            }
        }, dateTime);

        return listResult;
    }

    public Map<Long, BigDecimal> getPayedSumById(LocalDateTime dateTime) {

        String sql = "SELECT [UserId], sum (Payment.[sum]) totalPayed " +
                "FROM [Order] " +
                "JOIN Payment on Payment.OrderId = [Order].id " +
                "WHERE [Order].CreationTime = ? " +
                "GROUP BY [UserId]";

        Map<Long, BigDecimal> mapPaymentUser = new HashMap<>();

        jdbcTemplate.query(sql, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long userId = (Long) rs.getObject("UserId");
                BigDecimal payedSum = (BigDecimal) rs.getObject("totalPayed");
                mapPaymentUser.put(userId, payedSum);

                return null;
            }
        }, dateTime);
        return mapPaymentUser;
    }

    private Map<Long, List<GeneralPriceItemEntity>> getMapAdditions(Set<Long> setOrderId) {
        Map<Long, List<GeneralPriceItemEntity>> mapAddition = new HashMap<>();

        MapSqlParameterSource parameter = new MapSqlParameterSource();

        parameter.addValue("setOrderId", setOrderId);


        String sql = " SELECT [Order].id orderId, Addition.id [addition.id], Addition.[Name] [addition.name], " +
                "Addition.Cost [addition.price],  Addition.Active actAdd " +
                "FROM [Order]  " +
                "JOIN AdditionSelectedOrder ON AdditionSelectedOrder.OrderId = [Order].id  " +
                "JOIN Addition ON Addition.id = AdditionSelectedOrder.AdditionId  " +
                "WHERE [Order].id IN (:setOrderId ) " +
                "ORDER BY [Order].id DESC";


        namedParameterJdbcTemplate.query(sql, parameter, new RowMapper<GeneralPriceItemEntity>() {
                    @Override
                    public GeneralPriceItemEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                        GeneralPriceItemEntity addition = new GeneralPriceItemEntity();
                        addition.setId((Long) rs.getObject("addition.id"));
                        addition.setPrice((BigDecimal) rs.getObject("addition.price"));
                        addition.setName(rs.getNString("addition.name"));
                        addition.setActive(rs.getString("actAdd"));

                        Long orderId = (Long) rs.getObject("orderId");
                        mapAddition.putIfAbsent(orderId, new ArrayList<>());
                        mapAddition.get(orderId).add(addition);

                        return null;
                    }
                }
        );

        return mapAddition;
    }

    public PageEntity<OrderEntity> getPagedHistory(Long userId, int page, int pageSize) {

        String query = "declare @page int = ? ;" +
                " declare @pageSize int = ? ;" +
                " with tmpl as(" +
                " SELECT [Order].UserId userId , [Order].CreationTime creatTime , [Order].id orderId, [Order].Quantity quantity, [Order].CutInHalf cut, [Order].OrderSummaryId sumId,   \n" +
                " [Order].ShawarmaTypeId [dish.id], ShawarmaType.[Name] [dish.name], ShawarmaType.Cost [dish.price], ShawarmaType.Active actShaw , ShawarmaType.Halfable shawHalv, \n" +
                " ExtraItem.id [drink.id], ExtraItem.[Name] [drink.name], ExtraItem.Cost [drink.price], ExtraItem.Active actExtr," +
                " [Order].RemarkId [remark.id], Remark.[Name] [remark.name],  Remark.Active actRem " +
                " FROM [Order] " +
                " JOIN ShawarmaType ON ShawarmaType.id = [Order].ShawarmaTypeId  " +
                " JOIN ExtraItem ON ExtraItem.id = [Order].ExtraItemId " +
                " JOIN Remark ON Remark.id = [Order].RemarkId " +
                " WHERE [Order].UserId = ?" +
                " )" +
                " SELECT * FROM (select * from tmpl " +
                " order by creatTime DESC " +
                " offset @pageSize*(@page-1) rows fetch next @pageSize rows only ) sub " +
                " right join (SELECT count(*) FROM tmpl) c (total) on 1=1; ";


        Set<Long> ordersId = new HashSet<>();
        List<OrderEntity> listItems = new ArrayList<>();
        PageEntity<OrderEntity> resultPagedList = new PageEntity<>();


        listItems = jdbcTemplate.query(query, new RowMapper<OrderEntity>() {
            @Override
            public OrderEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                ShawarmaItemEntity dish = new ShawarmaItemEntity();
                dish.setId((Long) rs.getObject("dish.id"));
                dish.setName(rs.getNString("dish.name"));
                dish.setPrice(rs.getBigDecimal("dish.price"));
                dish.setActive(rs.getString("actShaw"));
                dish.setHalfAble(rs.getBoolean("shawHalv"));


                GeneralPriceItemEntity drink = new GeneralPriceItemEntity();
                drink.setId((Long) rs.getObject("drink.id"));
                drink.setActive(rs.getString("actExtr"));
                drink.setName(rs.getNString("drink.name"));
                drink.setPrice(rs.getBigDecimal("drink.price"));

                GeneralPriceItemEntity remark = new GeneralPriceItemEntity();
                remark.setId((Long) rs.getObject("remark.id"));
                remark.setName(rs.getNString("remark.name"));
                remark.setActive(rs.getString("actRem"));

                OrderEntity entity = new OrderEntity();
                entity.setOrderId((Long) rs.getObject("orderId"));
                entity.setDish(dish);
                entity.setDrink(drink);
                entity.setRemark(remark);
                entity.setCut(rs.getBoolean("cut"));
                entity.setQuantity((int) rs.getShort("quantity"));
                entity.setUserId((Long) rs.getObject("userId"));
                Timestamp creatTime = rs.getTimestamp("creatTime");
                if (creatTime != null) {
                    entity.setCreationTime(creatTime.toLocalDateTime());
                }
                entity.setOrderSummaryId((Long) rs.getObject("sumId"));

                Long id = (Long) rs.getObject("orderId");
                ordersId.add(id);

                resultPagedList.setTotalElements((int)rs.getShort("total"));

                return entity;
            }
        },page,pageSize,userId);


        Map<Long, List<GeneralPriceItemEntity>> map = getMapAdditions(ordersId);

        for (OrderEntity orderEntity : listItems) {
            orderEntity.setAdditions(map.get(orderEntity.getOrderId()));
        }

        resultPagedList.setResponseList(listItems);

        return  resultPagedList;
    }

}
