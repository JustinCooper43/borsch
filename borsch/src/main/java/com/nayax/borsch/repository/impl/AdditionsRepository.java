package com.nayax.borsch.repository.impl;

import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.user.UserEntity;
import com.nayax.borsch.repository.CrudItemGenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AdditionsRepository implements CrudItemGenericRepository<GeneralPriceItemEntity> {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public GeneralPriceItemEntity add(GeneralPriceItemEntity entity, TablesType nameTable) {
        String table = "Addition";
        String sql = " declare @table nvarchar(20) = ?  " +
                " declare @name nvarchar(12) = ?  " +
                " declare @cost decimal(10,2) = ?  " +
                " declare @SqlStr nvarchar(max)  " +
                " SET @SqlStr =  ' INSERT INTO '+  @table  +' ( [Name] , Cost ) " +
                "OUTPUT INSERTED.* VALUES ( '''+ @name +''' , '+  convert(nvarchar,@cost)+' ) ' " +
                " EXEC sp_executesql @SqlStr ";

        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(GeneralPriceItemEntity.class),
                table, entity.getName(), entity.getPrice()
        );
    }

    @Override
    public GeneralPriceItemEntity update(GeneralPriceItemEntity entity, TablesType nameTable) {
        String table = String.valueOf(nameTable);
        String sql = "UPDATE ? SET  [Name] = ? , Cost = ?  WHERE id = ? ";
        Optional<GeneralPriceItemEntity> itemFromDB = Optional.empty();
        int result;
        try {
            result = jdbcTemplate.update(sql,
                    table, entity.getName(), entity.getPrice(),
                    entity.getId());

            if (result != 1) {
                throw new EmptyResultDataAccessException(1);
            }

            Long id = entity.getId();
            itemFromDB = findById(id, nameTable);
            return itemFromDB.orElse(new GeneralPriceItemEntity());
        } catch (EmptyResultDataAccessException e) {
            // TODO perhaps needs logs
        }
        return itemFromDB.orElse(new GeneralPriceItemEntity());
    }

    @Override
    public Optional<GeneralPriceItemEntity> findById(Long id, TablesType nameTable) {
        String table = String.valueOf(nameTable);
        String sql = "SELECT [Name] , Cost FROM  ?  WHERE id = ?;";
        Optional<GeneralPriceItemEntity> result = Optional.empty();
        try {
            GeneralPriceItemEntity entity = jdbcTemplate.queryForObject(sql, new RowMapper<GeneralPriceItemEntity>() {
                @Override
                public GeneralPriceItemEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    GeneralPriceItemEntity entity = new GeneralPriceItemEntity();
                    entity.setId((Long) rs.getObject("id"));
                    entity.setPrice((BigDecimal) rs.getObject("Cost"));
                    entity.setName((String) rs.getObject("[Name]"));

                    return entity;
                }
            }, table, id);
            result = Optional.ofNullable(entity);
        } catch (EmptyResultDataAccessException e) {
            // TODO perhaps needs logs
            System.err.printf("Item %s not found\n", id == null ? null : id.toString());
        }
        return result;
    }

    @Override
    public List<GeneralPriceItemEntity> findAll(TablesType nameTable) {
        String table = String.valueOf(nameTable);
        String sql = "SELECT id, [Name], Cost FROM  ?  ";

        List<GeneralPriceItemEntity> listItems = new ArrayList<>();
        // jdbcTemplate.query doesn't throws EmptyResultDataAccessException
        listItems = jdbcTemplate.query(sql, new RowMapper<GeneralPriceItemEntity>() {
            @Override
            public GeneralPriceItemEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                GeneralPriceItemEntity entity = new GeneralPriceItemEntity();
                entity.setId((Long) rs.getObject("id"));
                entity.setName((String) rs.getObject("[Name]"));
                entity.setPrice((BigDecimal) rs.getObject("Cost"));
                return entity;
            }
        }, table);
        return listItems;
    }

    @Override
    public boolean delete(Long id, TablesType nameTable) {
        String table = String.valueOf(nameTable);
        String sql = "UPDATE ? SET Active = 'N' WHERE id = ?";

        try {
            int rows = jdbcTemplate.update(sql, table, id);
            return rows == 1;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
