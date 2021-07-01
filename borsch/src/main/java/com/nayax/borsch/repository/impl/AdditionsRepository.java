package com.nayax.borsch.repository.impl;

import com.nayax.borsch.model.entity.PageEntity;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.repository.CrudItemGenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class AdditionsRepository implements CrudItemGenericRepository<GeneralPriceItemEntity, TablesType> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public GeneralPriceItemEntity add(GeneralPriceItemEntity entity, TablesType nameTable) {

        String table = getNameTable(nameTable);

        String sql = "  declare @table nvarchar(20) =  ?;\n" +
                "  declare @name nvarchar(12) =  ? ;\n" +
                "  declare @cost decimal(10,2) = ? ;\n" +
                "  declare @active nvarchar(1) = ? ;\n" +
                "  declare @SqlStr nvarchar(max)  \n" +
                "  SET @SqlStr =  N' INSERT INTO ' +  @table  + ' ( [Name] , Cost , Active )  \n" +
                "  OUTPUT INSERTED.* VALUES ('+ @name +' , '+  convert(nvarchar,@cost)+ ', ''' + @active +''' ) ' \n" +
                "  EXEC sp_executesql @SqlStr  ";


        GeneralPriceItemEntity entity1 = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                    GeneralPriceItemEntity entityDB = new GeneralPriceItemEntity();
                    entityDB.setId((Long) rs.getObject("id"));
                    entityDB.setPrice((BigDecimal) rs.getObject("Cost"));
                    entityDB.setName((String) rs.getObject("Name"));
                    entityDB.setActive((String) rs.getObject("Active"));
                    return entityDB;
                },
                //TODO fix entity name
                table, "N'" + entity.getName() + "'", entity.getPrice(), entity.getActive()
        );
        return entity1;
    }

    @Override
    public GeneralPriceItemEntity update(GeneralPriceItemEntity entity, TablesType nameTable) {

        String table = getNameTable(nameTable);

        String sql = " declare @table nvarchar(20) = ? " +
                " declare @name nvarchar(12) =  ? " +
                " declare @cost decimal(10,2) = ? " +
                " declare @id bigint = ? " +
                " declare @SqlStr nvarchar(max) " +
                " SET @SqlStr =  N' UPDATE ' + @table + ' " +
                " SET  [Name] =  N''' + @name + ''' , Cost = '+  convert(nvarchar,@cost)+ ' WHERE id = ' +  convert(nvarchar,@id) " +
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

        }
        return itemFromDB.orElse(new GeneralPriceItemEntity());
    }

    @Override
    public Optional<GeneralPriceItemEntity> findById(Long id, TablesType nameTable) {
        String table = getNameTable(nameTable);
        String sql = " declare @table nvarchar(20) = ? " +
                " declare @id bigint = ? " +
                " declare @SqlStr nvarchar(max)  " +
                " SET  @SqlStr = ' SELECT id, [Name] , Cost FROM ' + @table + ' WHERE id = ' + convert(nvarchar,@id) " +
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
        String table = getNameTable(nameTable);
        String sql = " declare @table nvarchar(20) = ? " +
                " declare @SqlStr nvarchar(max) " +
                " set @SqlStr = N' SELECT id, [Name] , Cost  FROM ' + @table + ' WHERE Active LIKE ''Y'' ' " +
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


    public List<GeneralPriceItemEntity> findAllAdditionsById(Long dishId) {

        String sql = "SELECT  Addition.id addId, Addition.[Name] addName, Addition.Cost addCost, Addition.Active addAct  " +
                "FROM Addition JOIN AdditionAllowedShawarmaType shawAdd ON Addition.id = shawAdd.AllowedAdditionId " +
                "WHERE shawAdd.ShawarmaTypeId = ? AND Addition.Active = 'Y' ";

        List<GeneralPriceItemEntity> listItems;
        listItems = jdbcTemplate.query(sql, new RowMapper<GeneralPriceItemEntity>() {
            @Override
            public GeneralPriceItemEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                GeneralPriceItemEntity entity = new GeneralPriceItemEntity();
                entity.setId((Long) rs.getObject("addId"));
                entity.setName((String) rs.getObject("addName"));
                entity.setPrice((BigDecimal) rs.getObject("addCost"));
                return entity;
            }
        }, dishId);
        return listItems;
    }

    @Override
    public Optional<GeneralPriceItemEntity> delete(Long id, TablesType nameTable) {
        String table = getNameTable(nameTable);

        String sql = " declare @table nvarchar(20) = ? " +
                " declare @id bigint = ? " +
                " declare @SqlStr nvarchar(max) " +
                " SET @SqlStr =  ' UPDATE ' + @table +  ' SET Active = ''N'' WHERE id = ' + convert(nvarchar,@id) " +
                " EXEC sp_executesql @SqlStr ";

        try {
            int rows = jdbcTemplate.update(sql, table, id);
            return findById(id, nameTable);
        } catch (EmptyResultDataAccessException e) {
            return findById(id, nameTable);
        }
    }

    @Override
    public PageEntity<GeneralPriceItemEntity> findAllPage(int page, int pageSize, TablesType nameTable) {

        String table = getNameTable(nameTable);

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
                " right join (SELECT count(*) FROM @TableResult) c (total) on 1=1 ";

        List<GeneralPriceItemEntity> listItems;
        PageEntity<GeneralPriceItemEntity> listItemsPage = new PageEntity<>();

        listItems = jdbcTemplate.query(sql, new RowMapper<GeneralPriceItemEntity>() {
            @Override
            public GeneralPriceItemEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                GeneralPriceItemEntity entity = new GeneralPriceItemEntity();
                entity.setId((Long) rs.getObject("id"));
                entity.setName((String) rs.getObject("Name"));
                entity.setPrice((BigDecimal) rs.getObject("Cost"));
                listItemsPage.setTotalElements((int) rs.getShort("total"));
                return entity;
            }
        }, page, pageSize, table);
        listItemsPage.setResponseList(listItems);

        return listItemsPage;
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
