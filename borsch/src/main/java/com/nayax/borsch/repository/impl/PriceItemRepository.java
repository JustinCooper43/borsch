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

        String sql = "   select  id,[Name], Cost , 0 typ\n" +
                "   from ShawarmaType where ShawarmaType.Active LIKE 'Y' union \n" +
                "   select  id,[Name], Cost , 1 typ \n" +
                "   from Addition  where Addition.Active LIKE 'Y'  union \n" +
                "   select  id,[Name], Cost , 2 typ \n" +
                "   from   ExtraItem where ExtraItem.Active LIKE 'Y' order by typ ";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            PriceItemType entity = new PriceItemType();
            entity.setId((Long) rs.getObject("id"));
            entity.setName((String) rs.getObject("Name"));
            entity.setPrice((BigDecimal) rs.getObject("Cost"));
            entity.setType((Integer) rs.getObject("typ"));
            return entity;
        });
    }


}
