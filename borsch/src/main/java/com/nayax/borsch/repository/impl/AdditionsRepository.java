package com.nayax.borsch.repository.impl;

import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.user.UserEntity;
import com.nayax.borsch.repository.CrudItemGenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class AdditionsRepository implements CrudItemGenericRepository<GeneralPriceItemEntity, TablesType> {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public GeneralPriceItemEntity add(GeneralPriceItemEntity entity, TablesType nameTable) {
        String table = getNameTable(nameTable);


        String sql = " declare @table nvarchar(20) = ?  " +
                " declare @name nvarchar(12) = ?  " +
                " declare @cost decimal(10,2) = ?  " +
                " declare @active nvarchar(1) = ?  " +
                " declare @SqlStr nvarchar(max)  " +
                " SET @SqlStr =  ' INSERT INTO ' +  @table  + ' ( [Name] , Cost , Active )  " +
                " OUTPUT INSERTED.* VALUES ( '''+ @name +''' , '+  convert(nvarchar,@cost)+ ', ''' + @active +''' ) ' " +
                " EXEC sp_executesql @SqlStr ";


        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                    GeneralPriceItemEntity entity2 = new GeneralPriceItemEntity();
                    entity2.setId((Long) rs.getObject("id"));
                    entity2.setPrice((BigDecimal) rs.getObject("Cost"));
                    entity2.setName((String) rs.getObject("Name"));
                    entity2.setActive((String) rs.getObject("Active"));
                    return entity2;
                },
                table, entity.getName(), entity.getPrice(), entity.getActive()
        );
    }

    @Override
    public GeneralPriceItemEntity update(GeneralPriceItemEntity entity, TablesType nameTable) {


        String table = String.valueOf(nameTable);

        String sql = " declare @table nvarchar(20) = ? " +
                " declare @name nvarchar(12) =  ? " +
                " declare @cost decimal(10,2) = ? " +
                " declare @id bigint = ? " +
                " declare @SqlStr nvarchar(max) " +
                " SET @SqlStr =  ' UPDATE ' + @table + ' " +
                " SET  [Name] =  ''' + @name + ''' , Cost = '+  convert(nvarchar,@cost)+ ' WHERE id = ' +  convert(nvarchar,@id) " +
                " EXEC sp_executesql @SqlStr ";

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
        String sql = " declare @table nvarchar(20) = ? " +
                " declare @id bigint = ? " +
                " declare @SqlStr nvarchar(max)  " +
                " SET  @SqlStr = ' SELECT id, [Name] , Cost FROM ' + @table + ' WHERE id = ' + convert(nvarchar,@id) + ' and Active LIKE  ''Y'' ' " +
                " + 'AND (Active NOT LIKE ''N'' OR Active IS NULL)'  " +
                " EXEC sp_executesql @SqlStr ";

        Optional<GeneralPriceItemEntity> result = Optional.empty();
        try {
            GeneralPriceItemEntity entity = jdbcTemplate.queryForObject(sql, new RowMapper<GeneralPriceItemEntity>() {
                @Override
                public GeneralPriceItemEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    GeneralPriceItemEntity entity = new GeneralPriceItemEntity();
                    entity.setId((Long) rs.getObject("id"));
                    entity.setPrice((BigDecimal) rs.getObject("Cost"));
                    entity.setName((String) rs.getObject("Name"));

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
        String sql = " declare @table nvarchar(20) = ? " +
                " declare @SqlStr nvarchar(max) " +
                " set @SqlStr = ' SELECT id, [Name] , Cost  FROM ' + @table + ' WHERE Active LIKE ''Y'' ' " +
                " EXEC sp_executesql @SqlStr";

        List<GeneralPriceItemEntity> listItems;
        listItems = jdbcTemplate.query(sql, new RowMapper<GeneralPriceItemEntity>() {
            @Override
            public GeneralPriceItemEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                GeneralPriceItemEntity entity = new GeneralPriceItemEntity();
                entity.setId((Long) rs.getObject("id"));
                entity.setName((String) rs.getObject("Name"));
                entity.setPrice((BigDecimal) rs.getObject("Cost"));
                return entity;
            }
        }, table);
        return listItems;
    }

    @Override
    public GeneralPriceItemEntity delete(Long id, TablesType nameTable) {
        String table = String.valueOf(nameTable);
        GeneralPriceItemEntity deletedEntity = findById(id, nameTable).orElse(new GeneralPriceItemEntity());

        String sql = " declare @table nvarchar(20) = ? " +
                " declare @id bigint = ? " +
                " declare @SqlStr nvarchar(max) " +
                " SET @SqlStr =  ' UPDATE ' + @table +  ' SET Active = ''N'' WHERE id = ' + convert(nvarchar,@id) " +
                " EXEC sp_executesql @SqlStr ";

        try {
            int rows = jdbcTemplate.update(sql, table, id);
            return deletedEntity;
        } catch (EmptyResultDataAccessException e) {
            return deletedEntity;
        }
    }

    @Override
    public List<GeneralPriceItemEntity> findAllPage(int page, int pageSize, TablesType nameTable) {

        String table = String.valueOf(nameTable);
        String sql = " declare @page int = ?  " +
                " declare @pageSize int = ?  " +
                " declare @table nvarchar(20) = ? " +
                " declare @SqlStr nvarchar(max) " +
                " declare @TableResult as table ( id bigint , [Name] nvarchar(200), Cost decimal (10,2) ) " +
                " SET @SqlStr = ' SELECT id, [Name] , Cost  FROM ' + @table + ' WHERE Active LIKE ''Y'' ' " +
                " insert into @TableResult  " +
                " EXEC sp_executesql  @SqlStr " +
                " select * from (select* " +
                " from @TableResult " +
                " order by id " +
                " offset @pageSize*(@page-1) rows fetch next @pageSize rows only ) sub  " +
                " right join (SELECT count(*) FROM @TableResult) c (total) on 1=1 " ;

        List<GeneralPriceItemEntity> listItems;
        listItems = jdbcTemplate.query(sql, new RowMapper<GeneralPriceItemEntity>() {
            @Override
            public GeneralPriceItemEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                GeneralPriceItemEntity entity = new GeneralPriceItemEntity();
                entity.setId((Long) rs.getObject("id"));
                entity.setName((String) rs.getObject("Name"));
                entity.setPrice((BigDecimal) rs.getObject("Cost"));
                return entity;
            }
        }, page, pageSize, table);
        return listItems;

    }

    private String getNameTable(TablesType tablesType) {
        String result = null;
        for (TablesType tab : TablesType.values()) {
            if (tab == tablesType) {
                result = tab.nameTable;
            }
        }
        return result;
    }

}
