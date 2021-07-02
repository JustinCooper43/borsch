package com.nayax.borsch.repository.impl;

import com.nayax.borsch.model.entity.assortment.AssortmentRespEntity;
import com.nayax.borsch.model.entity.assortment.AssortmentUpEntity;
import com.nayax.borsch.model.entity.assortment.GeneralPriceItemEntity;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
public class RepositoryAssortmentImpl {

    @Autowired
    private  JdbcTemplate jdbcTemplate;
    @Autowired
    private  NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    RepositoryShawarmaTypeImpl shawarmaType;



    public List<AssortmentRespEntity> findAll() {
        List<AssortmentRespEntity> list = new ArrayList<>();
        Map<ShawarmaItemEntity, List<GeneralPriceItemEntity>> additions = new HashMap<>();
        Set<Long> listShawarmaId = new HashSet<>();

        String sql = "Select sh.id shawId,sh.[Name] shawName,sh.Cost shawCost,sh.Halfable shawHalf,sh.Active shawAct,\n" +
                "a.id adId, a.[Name] adName,a.Cost adCost, a.Active adAct\n" +
                "from ShawarmaType sh\n" +
                "left join AdditionAllowedShawarmaType addShawarm on sh.id = addShawarm.ShawarmaTypeId and sh.Active = 'Y' \n" +
                "left join Addition a on addShawarm.AllowedAdditionId = a.id and A.Active = 'Y'";

        jdbcTemplate.query(sql, (RowMapper<AssortmentRespEntity>) (rs, rowNum) -> {
            ShawarmaItemEntity shawarma = new ShawarmaItemEntity();
            shawarma.setId((Long) rs.getObject("shawId"));
            shawarma.setName((String) rs.getObject("shawName"));
            shawarma.setPrice((BigDecimal) rs.getObject("shawCost"));
            shawarma.setHalfAble(rs.getInt("shawHalf") > 0);
            additions.putIfAbsent(shawarma, new ArrayList<>());
            listShawarmaId.add(shawarma.getId());
            ///////////////
            if (rs.getObject("adId") != null) {
                GeneralPriceItemEntity addition = new GeneralPriceItemEntity();
                addition.setId((Long) rs.getObject("adId"));
                addition.setName((String) rs.getObject("adName"));
                addition.setPrice((BigDecimal) rs.getObject("adCost"));
                addition.setActive(rs.getString("adAct"));
                additions.get(shawarma).add(addition);
            }
            return null;
            });
        if (listShawarmaId.size() != 0) {
            Map<ShawarmaItemEntity, List<GeneralPriceItemEntity>> remarks = shawarmaType.getAllRemarks(listShawarmaId);

            for (ShawarmaItemEntity var : additions.keySet()){
                AssortmentRespEntity entity = new AssortmentRespEntity();
                entity.setDish(var);
                entity.setAdditions(additions.get(var));
                entity.setHalfAble(var.isHalfAble());
                entity.setRemarks(remarks.get(var));
                list.add(entity);
            }
        }
        return list;
    }



    public AssortmentRespEntity update(AssortmentUpEntity entity){

        String sqlUpdate = "update RemarkAllowedShawarmaType set Active = 'N' where ShawarmaTypeId = ? ;" +
                           "update AdditionAllowedShawarmaType set Active = 'N' where ShawarmaTypeId = ? ;" +
                           "update ShawarmaType set Halfable = ? where id = ?";

//        String sqlInsert1 = "Insert into RemarkAllowedShawarmaType (ShawarmaTypeId,RemarkId,Active) values(?,?,?)";
        String sqlInsert2 = "Insert into AdditionAllowedShawarmaType (ShawarmaTypeId,AllowedAdditionId,Active) values(?,?,?)";
        jdbcTemplate.update(sqlUpdate,entity.getDish(),entity.getDish(),entity.isHalfAble() ? 1 : 0, entity.getDish());
//        jdbcTemplate.batchUpdate(sqlInsert1, new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps, int i) throws SQLException {
//                ps.setLong(1,entity.getDish());
//                ps.setLong(2,entity.getRemarksId().get(i));
//                ps.setString(3,"Y");
//            }
//            @Override
//            public int getBatchSize() {
//                return entity.getRemarksId().size();
//            }
//        });
        jdbcTemplate.batchUpdate(sqlInsert2, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1,entity.getDish());
                ps.setLong(2,entity.getAdditionsId().get(i));
                ps.setString(3,"Y");
            }
            @Override
            public int getBatchSize() {
                return entity.getAdditionsId().size();
            }
        });
        return null;
    }
}