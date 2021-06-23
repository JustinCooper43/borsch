package com.nayax.borsch.repository.impl;

import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import com.nayax.borsch.model.entity.order.OrderEntity;
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
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class OrderItemRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<OrderEntity> getPagedOrders(Long userId, LocalDateTime dateTime, int page, int pageSize) {

        String sql = " declare @page int = ? ;" +
                " declare @pageSize int = ? ;" +
                " with tmpl as( " +
                " SELECT [Order].UserId userId , [Order].CreationTime creatTime , [Order].id orderId, [Order].Quantity quantity, [Order].CutInHalf cut, [Order].OrderSummaryId sumId,    " +
                " [Order].ShawarmaTypeId [dish.id], ShawarmaType.[Name] [dish.name], ShawarmaType.Cost [dish.price], ShawarmaType.Active actShaw , ShawarmaType.Halfable shawHalv, " +
                " ExtraItem.id [drink.id], ExtraItem.[Name] [drink.name], ExtraItem.Cost [drink.price], ExtraItem.Active actExtr ,  " +
                " [Order].RemarkId [remark.id], Remark.[Name] [remark.name],  Remark.Active actRem  " +
                " FROM [Order] " +
                " JOIN ShawarmaType ON ShawarmaType.id = [Order].ShawarmaTypeId  " +
                " JOIN ExtraItem ON ExtraItem.id = [Order].ExtraItemId  " +
                " JOIN Remark ON Remark.id = [Order].RemarkId  " +
                " WHERE [Order].UserId = ? AND [Order].CreationTime = ? " +
                " ) " +
                " SELECT * FROM (select * from tmpl  " +
                "  order by creatTime DESC " +
                "  offset @pageSize*(@page-1) rows fetch next @pageSize rows only ) sub  " +
                " right join (SELECT count(*) FROM tmpl) c (total) on 1=1; ";

        Set<Long> setOrderId = new HashSet<>();

        List<OrderEntity> listOrders = new ArrayList<>();
        List<OrderEntity> listItems = new ArrayList<>();
        listItems = jdbcTemplate.query(sql, new RowMapper<OrderEntity>() {
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

                listOrders.add(entity);

                Long orderId = (Long) rs.getObject("orderId");
                setOrderId.add(orderId);

                return entity;
            }
        }, page, pageSize, userId, dateTime);

        Map<Long, List<GeneralPriceItemEntity>> map =  getMapAdditions(setOrderId,dateTime);

        for(OrderEntity var: listOrders){
            var.setAdditions(map.get(var.getOrderId()));
        }
        return listOrders;
    }

    private Map<Long, List<GeneralPriceItemEntity>> getMapAdditions(Set<Long> setOrderId , LocalDateTime dateTime ) {
        Map<Long, List<GeneralPriceItemEntity>> mapAddition = new HashMap<>();

        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("setOrderId",setOrderId);
        parameter.addValue("dateTime", dateTime);

        String sql = " SELECT [Order].id orderId, Addition.id [addition.id], Addition.[Name] [addition.name], " +
                "Addition.Cost [addition.price],  Addition.Active actAdd " +
                "FROM [Order]  " +
                "JOIN AdditionSelectedOrder ON AdditionSelectedOrder.OrderId = [Order].id  " +
                "JOIN Addition ON Addition.id = AdditionSelectedOrder.AdditionId  " +
                "WHERE [Order].UserId IN (:setOrderId ) AND [Order].CreationTime = :dateTime " +
                "ORDER BY [Order].id DESC";


        namedParameterJdbcTemplate.query(sql, parameter, new RowMapper<GeneralPriceItemEntity>() {
                    @Override
                    public GeneralPriceItemEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                        GeneralPriceItemEntity addition = new GeneralPriceItemEntity();
                        addition.setId((Long)rs.getObject("addition.id"));
                        addition.setPrice((BigDecimal) rs.getObject("addition.price"));
                        addition.setName(rs.getNString("addition.name"));
                        addition.setActive(rs.getString("actAdd"));

                        Long orderId = (Long) rs.getObject("orderId");
                        mapAddition.get(orderId).add(addition);
                        return null;
                    }
                }
        );

        return mapAddition;
    }

}
