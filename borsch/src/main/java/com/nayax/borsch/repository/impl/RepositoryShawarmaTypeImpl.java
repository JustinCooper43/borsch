package com.nayax.borsch.repository.impl;

import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class RepositoryShawarmaTypeImpl{

    @Autowired
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();


    public ShawarmaItemEntity add(ShawarmaItemEntity entity) {
        String sql = "Insert into ShawarmaType ([Name],Cost,Halfable,active) \n" +
                "values(?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                ps.setString(1,entity.getName());
                ps.setBigDecimal(2,entity.getPrice());
                ps.setInt(3,entity.isHalfAble() ? 1 : 0);
                ps.setString(4,"Y");
                return ps;
            },keyHolder);

        }catch (EmptyResultDataAccessException e){
            System.err.println("Cannot be insert Shawarma type!!!!");
        }
       Optional<ShawarmaItemEntity> result = findById(Objects.requireNonNull(keyHolder.getKey()).longValue());
       return result.orElse(new ShawarmaItemEntity());
//        return  jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(ShawarmaItemEntity.class),
//               entity.getName(),entity.getPrice(),entity.isHalfAble() ? 1 : 0,"Y");
    }



    public ShawarmaItemEntity update(ShawarmaItemEntity entity) {
        String sql = "Update ShawarmaType set [Name] = ?, Cost = ?, Halfable = ? where id = ?;";
        try {
           jdbcTemplate.update(sql,
                    entity.getName(), entity.getPrice(), entity.isHalfAble(), entity.getId());
        }catch (EmptyResultDataAccessException e){
            System.err.println("Cannot be update Shawarma type!!!!");
        }
        return findById(entity.getId()).orElse(new ShawarmaItemEntity());
    }

    public Optional<ShawarmaItemEntity> findById(Long id) {
        ShawarmaItemEntity entity = new ShawarmaItemEntity();
        String sql = "Select ShawarmaType.id, ShawarmaType.[Name],ShawarmaType.Cost,ShawarmaType.Halfable, ShawarmaType.Active active from ShawarmaType where id = ? and active = 'Y'";
        try {
           entity = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
               ShawarmaItemEntity entity1 = new ShawarmaItemEntity();
               entity1.setId((Long) rs.getObject("id"));
               entity1.setName(rs.getString("Name"));
               entity1.setPrice(rs.getBigDecimal("Cost"));
               entity1.setHalfAble(rs.getInt("Halfable") > 0);
               return entity1;
           },id);
        }catch (EmptyResultDataAccessException e){
            System.err.printf("Shawarma %s not found\n", id == null ? null : id.toString());
        }
        return Optional.ofNullable(entity);
    }

    public List<ShawarmaItemEntity> findAll() {
        List<ShawarmaItemEntity> list = new ArrayList<>();
        String sql = "Select ShawarmaType.id, ShawarmaType.[Name],ShawarmaType.Cost,ShawarmaType.Halfable from ShawarmaType where active = 'Y'";
        try {
            list = jdbcTemplate.query(sql, (rs, rowNum) -> {
                ShawarmaItemEntity entity = new ShawarmaItemEntity();
                entity.setId((Long) rs.getObject("id"));
                entity.setName(rs.getString("Name"));
                entity.setPrice(rs.getBigDecimal("Cost"));
                entity.setHalfAble(rs.getInt("Halfable") > 0);
                return entity;
            });
        }catch (EmptyResultDataAccessException e){
            System.err.println("Shawarma not found!!!");
        }
        return list;
    }

    public List<ShawarmaItemEntity> findAll(int number,int sizePage) {
        List<ShawarmaItemEntity> list = new ArrayList<>();
        String sql = "declare @page int = ?; \n" +
                "declare @pageSize int = ?; \n" +
                "WITH cte AS ( \n" +
                " SELECT * FROM ShawarmaType \n" +
                " where Active = 'Y' and Active is not null \n" +
                ") \n" +
                "SELECT * FROM (select * from cte \n" +
                " order by id \n" +
                " offset @pageSize*(@page-1) rows fetch next @pageSize rows only ) sub \n" +
                "right join (SELECT count(*) FROM cte) c (total) on 1=1;";
        try {
            list = jdbcTemplate.query(sql, (rs, rowNum) -> {
                ShawarmaItemEntity entity = new ShawarmaItemEntity();
                entity.setId((Long) rs.getObject("id"));
                entity.setName(rs.getString("Name"));
                entity.setPrice(rs.getBigDecimal("Cost"));
                entity.setHalfAble(rs.getInt("Halfable") > 0);
                return entity;
            },number,sizePage);
        }catch (EmptyResultDataAccessException e){
            System.err.println("Shawarma not found!!!");
        }
        return list;
    }



    public Optional<ShawarmaItemEntity> delete(Long id) {
        String sql = "Update ShawarmaType set active = 'N' where id = ?";
        Optional<ShawarmaItemEntity> deleted = findById(id);
        try {
            jdbcTemplate.update(sql,id);
        }catch (EmptyResultDataAccessException e){
            System.err.printf("Shawarma type %s can not be deleted!!\n",id);
        }
        return deleted;
    }

    public Optional<ShawarmaItemEntity> delete(ShawarmaItemEntity entity) {
        return delete(entity.getId());
    }

   // public List<ShawarmaItemEntity> getAllByFilter(Object filter) {
   //     return null;
   // }
}
