package com.nayax.borsch.repository.impl;

import com.nayax.borsch.model.entity.PageEntity;
import com.nayax.borsch.model.entity.assortment.AssortmentRespEntity;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

@Repository
public class RepositoryShawarmaTypeImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ShawarmaItemEntity add(ShawarmaItemEntity entity) {
        String sql = "Insert into ShawarmaType ([Name],Cost,Halfable,active) \n" +
                "values(?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, entity.getName());
                ps.setBigDecimal(2, entity.getPrice());
                ps.setInt(3, entity.isHalfAble() ? 1 : 0);
                ps.setString(4, "Y");
                return ps;
            }, keyHolder);

        } catch (EmptyResultDataAccessException e) {
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
        } catch (EmptyResultDataAccessException e) {
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
            }, id);
        } catch (EmptyResultDataAccessException e) {
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
        } catch (EmptyResultDataAccessException e) {
            System.err.println("Shawarma not found!!!");
        }
        return list;
    }

    public PageEntity<ShawarmaItemEntity> findAll(int number, int sizePage) {
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

        PageEntity<ShawarmaItemEntity> pageEntity = new PageEntity<>();
        try {
            list = jdbcTemplate.query(sql, (rs, rowNum) -> {
                ShawarmaItemEntity entity = new ShawarmaItemEntity();
                entity.setId((Long) rs.getObject("id"));
                entity.setName(rs.getString("Name"));
                entity.setPrice(rs.getBigDecimal("Cost"));
                entity.setHalfAble(rs.getInt("Halfable") > 0);
                pageEntity.setTotalElements((int) rs.getShort("total"));
                return entity;
            }, number, sizePage);
        } catch (EmptyResultDataAccessException e) {
            System.err.println("Shawarma not found!!!");
        }
        pageEntity.setResponseList(list);
        return pageEntity;
    }


    public Optional<ShawarmaItemEntity> delete(Long id) {
        String sql = "Update ShawarmaType set active = 'N' where id = ?";
        Optional<ShawarmaItemEntity> deleted = findById(id);
        try {
            jdbcTemplate.update(sql, id);
        } catch (EmptyResultDataAccessException e) {
            System.err.printf("Shawarma type %s can not be deleted!!\n", id);
        }
        return deleted;
    }

    public Optional<ShawarmaItemEntity> delete(ShawarmaItemEntity entity) {
        return delete(entity.getId());
    }

    // public List<ShawarmaItemEntity> getAllByFilter(Object filter) {
    //     return null;
    // }


    public Map<ShawarmaItemEntity, List<GeneralPriceItemEntity>> getAdditionsByShawarwa(Set<Long> ids) {
        String sql = "  Select sh.id shawId,sh.[Name] shawName,sh.Cost shawCost,sh.Halfable shawHalf,sh.Active shawAct,\n" +
                "a.id addId, a.[Name] addName, a.Active addAct\n" +
                "from ShawarmaType sh\n" +
                "left join AdditionAllowedShawarmaType addAll on sh.id = addAll.ShawarmaTypeId and sh.Active = 'Y' \n" +
                "left join Addition  a  on addAll.AllowedAdditionId = a.id and a.Active = 'Y' \n" +
                "where sh.id in (:ids)";
        Map<ShawarmaItemEntity, List<GeneralPriceItemEntity>> additions = new HashMap<>();
        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);
        namedParameterJdbcTemplate.query(sql, parameters, (RowMapper<AssortmentRespEntity>) (rs, rowNum) -> {
            ShawarmaItemEntity shawarma = new ShawarmaItemEntity();
            shawarma.setId((Long) rs.getObject("shawId"));
            shawarma.setName((String) rs.getObject("shawName"));
            shawarma.setPrice((BigDecimal) rs.getObject("shawCost"));
            shawarma.setHalfAble(rs.getInt("shawHalf") > 0);
            additions.putIfAbsent(shawarma, new ArrayList<>());

            if (rs.getObject("addId") != null) {
                GeneralPriceItemEntity remark = new GeneralPriceItemEntity();
                remark.setId((Long) rs.getObject("addId"));
                remark.setName((String) rs.getObject("addName"));
                remark.setActive((String) rs.getObject("addAct"));
                additions.get(shawarma).add(remark);
            }
            return null;
        });
        return additions;
    }
}
