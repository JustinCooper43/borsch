package com.nayax.borsch.repository.impl;

import com.nayax.borsch.model.entity.assortment.AssortmentRespEntity;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import com.nayax.borsch.model.entity.user.UserEntity;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.crypto.MacSpi;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RepositoryAssortmentImpl {

    @Autowired
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();


    public List<AssortmentRespEntity> findAll(int number, int sizePage) {
        List<AssortmentRespEntity> list = new ArrayList<>();
        Map<ShawarmaItemEntity, List<GeneralPriceItemEntity>> additions = new HashMap<>();
        Map<ShawarmaItemEntity, List<GeneralPriceItemEntity>> remarks = new HashMap<>();
        String sql = "declare @page int = ?;\n" +
                "declare @pageSize int = ?;\n" +
                " \n" +
                "with tmpl as (\n" +
                " \n" +
                "select ShawarmaType.id shawId , ShawarmaType.[Name] shawName ,\n" +
                "ShawarmaType.Cost shawCost, ShawarmaType.Halfable shawHalf, ShawarmaType.Active shawAct,\n" +
                "Addition.id adId, Addition.[Name] adName , Addition.Cost adCost, Addition.Active adAct,\n" +
                "Remark.id remId, Remark.[Name] remName, Remark.Active remAct\n" +
                " \n" +
                "from ShawarmaType \n" +
                "left join AdditionAllowedShawarmaType addShaw on ShawarmaType.id = addShaw.ShawarmaTypeId \n" +
                "left join Addition on addShaw.AllowedAdditionId = Addition.id \n" +
                "left join RemarkAllowedShawarmaType remShaw on remShaw.ShawarmaTypeId = ShawarmaType.id \n" +
                "left join Remark on Remark.id = remShaw.RemarkId\n" +
                " \n" +
                "WHERE (ShawarmaType.Active LIKE 'Y' ) and\n" +
                "(addShaw.Active LIKE 'Y' ) and\n" +
                "(Addition.Active LIKE 'Y' ) and\n" +
                "(remShaw.Active LIKE 'Y') and\n" +
                "(Remark.Active LIKE 'Y' )\n" +
                ")\n" +
                "select * from (select*\n" +
                " \n" +
                "from tmpl\n" +
                "order by shawId, shawName, shawCost\n" +
                " \n" +
                " offset @pageSize*(@page-1) rows fetch next @pageSize rows only ) sub\n" +
                "right join (SELECT count(*) FROM tmpl) c (total) on 1=1";
        List<AssortmentRespEntity> aa = new ArrayList<>();
        list = jdbcTemplate.query(sql, new RowMapper<AssortmentRespEntity>() {
            @Override
            public AssortmentRespEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                ShawarmaItemEntity shawarma = new ShawarmaItemEntity();
                shawarma.setId((Long) rs.getObject("shawId"));
                shawarma.setName((String) rs.getObject("shawName"));
                shawarma.setPrice((BigDecimal) rs.getObject("shawCost"));
                shawarma.setHalfAble(rs.getInt("shawHalf") > 0);
                remarks.putIfAbsent(shawarma, new ArrayList<>());
                additions.putIfAbsent(shawarma, new ArrayList<>());
                /////////////////////////
                GeneralPriceItemEntity addition = new GeneralPriceItemEntity();
                addition.setId(rs.getLong("adId"));
                addition.setName((String) rs.getObject("adName"));
                addition.setPrice((BigDecimal) rs.getObject("adCost"));
                additions.get(shawarma).add(addition);
                /////////////////////////
                GeneralPriceItemEntity remark = new GeneralPriceItemEntity();
                remark.setId((Long) rs.getObject("remId"));
                remark.setName((String) rs.getObject("remName"));
                remark.setActive((String) rs.getObject("remAct"));
                remarks.get(shawarma).add(remark);

                return new AssortmentRespEntity();
            }
        }, number, sizePage);

        for (ShawarmaItemEntity var : remarks.keySet()) {
            AssortmentRespEntity entity = new AssortmentRespEntity();
            entity.setDish(var);
            entity.setAdditions(additions.get(var));
            entity.setRemarks(remarks.get(var));
            list.add(entity);
        }
        return list;
    }
}