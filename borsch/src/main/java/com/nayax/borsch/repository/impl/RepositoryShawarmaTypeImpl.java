package com.nayax.borsch.repository.impl;

import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import com.nayax.borsch.model.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RepositoryShawarmaTypeImpl{

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public ShawarmaItemEntity add(ShawarmaItemEntity entity) {
        return null;
    }

    public ShawarmaItemEntity update(ShawarmaItemEntity entity) {
        String sql = "Update ShawarmaType set [Name] = ?, Cost = ?, Halfable = ? where id = ?;";
        int result = 0;
        try {
            result = jdbcTemplate.update(sql,
                    entity.getName(), entity.getPrice(), entity.getHalfAble(), entity.getId());
        }catch (EmptyResultDataAccessException e){
            System.out.println("Cannot be update Shawarma type!!!!");
        }
        return findById(entity.getId()).get();
    }

    public Optional<ShawarmaItemEntity> findById(Long id) {
        ShawarmaItemEntity entity = new ShawarmaItemEntity();
        String sql = "Select ShawarmaType.id, ShawarmaType.[Name],ShawarmaType.Cost,ShawarmaType.Halfable, ShawarmaType.Active active from ShawarmaType where id = ? and active = 'Y'";
        try {
           entity = jdbcTemplate.queryForObject(sql, new RowMapper<ShawarmaItemEntity>() {
                @Override
                public ShawarmaItemEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ShawarmaItemEntity entity = new ShawarmaItemEntity();
                    entity.setId((Long) rs.getObject("id"));
                    entity.setName(rs.getString("Name"));
                    entity.setPrice(rs.getBigDecimal("Cost"));
                    entity.setHalfAble(rs.getInt("Halfable") > 0);
                    return entity;
                }
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
            list = jdbcTemplate.query(sql, new RowMapper<ShawarmaItemEntity>() {
                @Override
                public ShawarmaItemEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ShawarmaItemEntity entity = new ShawarmaItemEntity();
                    entity.setId((Long) rs.getObject("id"));
                    entity.setName(rs.getString("Name"));
                    entity.setPrice(rs.getBigDecimal("Cost"));
                    entity.setHalfAble(rs.getInt("Halfable") > 0);
                    return entity;
                }
            });
        }catch (EmptyResultDataAccessException e){
            System.out.println("Shawarma not found!!!");
        }
        return list;
    }

    public boolean delete(Long id) {
        return false;
    }

    public boolean delete(ShawarmaItemEntity entity) {
        return false;
    }

    public List<ShawarmaItemEntity> getAllByFilter(Object filter) {
        return null;
    }
}
