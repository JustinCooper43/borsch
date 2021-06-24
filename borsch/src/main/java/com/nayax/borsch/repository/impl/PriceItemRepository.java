package com.nayax.borsch.repository.impl;

import com.nayax.borsch.model.entity.assortment.PriceItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class PriceItemRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<PriceItemType> getAll() {

        String sql = " select  [Name], Cost , 0 typ  " +
                " from ShawarmaType where ShawarmaType.Active LIKE 'Y' union " +
                " select  [Name], Cost , 1 typ  " +
                " from Addition  where Addition.Active LIKE 'Y'  union " +
                " select  [Name], Cost , 2 typ  " +
                " from   ExtraItem where ExtraItem.Active LIKE 'Y' order by typ ";

        return jdbcTemplate.query(sql, new RowMapper<PriceItemType>() {
            @Override
            public PriceItemType mapRow(ResultSet rs, int rowNum) throws SQLException {
                PriceItemType entity = new PriceItemType();
                entity.setName((String) rs.getObject("Name"));
                entity.setPrice((BigDecimal) rs.getObject("Cost"));
                entity.setType((Integer) rs.getObject("typ"));
                return entity;
            }
        });
    }


}
