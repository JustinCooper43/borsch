package com.nayax.borsch.repository.impl;

import com.nayax.borsch.exceptions.NotUpdateException;
import com.nayax.borsch.model.entity.user.CashierEntity;
import com.nayax.borsch.model.entity.user.ProfileEntity;
import com.nayax.borsch.model.entity.user.UserEntity;
import com.nayax.borsch.repository.GenericCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ProfileRepositoryImplementation implements GenericCrudRepository<ProfileEntity, Object> {


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public ProfileEntity add(ProfileEntity entity) {

        String sqlUser = "INSERT INTO [User] (Active, RoleId ,Email , FirstName ,LastName , PhoneNumber)" +
                " values  (?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sqlUser, new String[]{"id"});
                ps.setNString(1, entity.getUserEntity().getActive());
                ps.setLong(2, entity.getUserEntity().getRoleId());
                ps.setNString(3, entity.getUserEntity().geteMail());
                ps.setNString(4, entity.getUserEntity().getFirstName());
                ps.setNString(5, entity.getUserEntity().getLastName());
                ps.setNString(6, entity.getUserEntity().getPhone());
                return ps;
            }
        },keyHolder);

        Long i = Objects.requireNonNull(keyHolder.getKey()).longValue();
        entity.getCashierEntity().setCashierId(i);

        String sqlCashier = "INSERT INTO Cashier " +
                "( UserId, CashPaymentAllowed " +
                ",CCNumber, CCBank, CCNote, CCQRCode)" +
                " OUTPUT INSERTED.* VALUES (?,?, ?, ?, ?, ?)";

        CashierEntity cashierEntity = jdbcTemplate.queryForObject(sqlCashier, new BeanPropertyRowMapper<>(CashierEntity.class),
                 entity.getCashierEntity().getCashierId(),
                 entity.getCashierEntity().isCashPaymentAllowed(), entity.getCashierEntity().getCardNumber(),
                entity.getCashierEntity().getCardBank(), entity.getCashierEntity().getCardNote(),
                entity.getCashierEntity().getCardQrCode());




        return findById(entity.getUserEntity().getId()).orElseThrow(NotUpdateException::new);

    }

    @Override
    public ProfileEntity update(ProfileEntity entity) throws NotUpdateException {




        return null;
    }

    @Override
    public Optional<ProfileEntity> findById(Long id) {

        String sql = "SELECT u.id  userId, u.Active activeUser, u.RoleId roleId, \n" +
                " u.Email userEmail, u.FirstName fName, u.LastName lName, u.PhoneNumber phNumber, r.[Name] roleName, \n" +
                " c.UserId cashierId, c.CashPaymentAllowed CashPay, c.CCNumber Card, c.CCBank Bank, c.CCNote Note, c.CCQRCode QrCode\n" +
                " FROM [User] u\n" +
                " LEFT JOIN Cashier c ON u.id = c.UserId \n" +
                " LEFT JOIN  [Role] r on u.RoleId = r.id \n" +
                " WHERE u.id = ? AND u.Active = 'Y';";

        Optional<ProfileEntity> result = Optional.empty();

        try {
            ProfileEntity profileEntity = jdbcTemplate.queryForObject(sql, new RowMapper<ProfileEntity>() {

                @Override
                public ProfileEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

                    ProfileEntity profile = new ProfileEntity();
                    CashierEntity cashier = new CashierEntity();
                    UserEntity user = new UserEntity();

                    user.setId((Long) rs.getObject("userId"));
                    user.setActive(rs.getString("activeUser"));
                    user.setRoleId((Long) rs.getObject("roleId"));
                    user.seteMail(rs.getNString("userEmail"));
                    user.setFirstName(rs.getNString("fName"));
                    user.setLastName(rs.getNString("lName"));
                    user.setPhone(rs.getNString("phNumber"));
                    user.setRoleName(rs.getNString("roleName"));

                    cashier.setCashierId((Long) rs.getObject("cashierId"));
                    cashier.setCashPaymentAllowed(rs.getBoolean("CashPay"));
                    cashier.setCardNumber(rs.getNString("Card"));
                    cashier.setCardBank(rs.getNString("Bank"));
                    cashier.setCardNote(rs.getNString("Note"));
                    cashier.setCardQrCode(rs.getNString("QrCode"));

                    profile.setUserEntity(user);
                    profile.setCashierEntity(cashier);

                    return profile;
                }
            }, id);
            result = Optional.ofNullable(profileEntity);
        } catch (EmptyResultDataAccessException e) {

            System.err.printf("Profile %s not found\n", id == null ? null : id.toString());
        }
        return result;
    }


    @Override
    public List<ProfileEntity> findAll() {

        String query = "SELECT u.id  userId, u.Active activeUser, u.RoleId roleId, \n" +
                " u.Email userEmail, u.FirstName fName, u.LastName lName, u.PhoneNumber phNumber, r.[Name] roleName, \n" +
                " c.UserId cashierId, c.CashPaymentAllowed CashPay, c.CCNumber Card, c.CCBank Bank, c.CCNote Note, c.CCQRCode QrCode\n" +
                " FROM [User] u\n" +
                " LEFT JOIN Cashier c ON u.id = c.UserId \n" +
                " LEFT JOIN  [Role] r on u.RoleId = r.id \n" +
                " WHERE u.Active = 'Y';";


        List<ProfileEntity> entityList;

            entityList = jdbcTemplate.query(query, new RowMapper<ProfileEntity>() {

                @Override
                public ProfileEntity mapRow(ResultSet rs, int rowNum) throws SQLException {


                    ProfileEntity profile = new ProfileEntity();
                    CashierEntity cashier = new CashierEntity();
                    UserEntity user = new UserEntity();

                    user.setId((Long) rs.getObject("userId"));
                    user.setActive(rs.getString("activeUser"));
                    user.setRoleId((Long) rs.getObject("roleId"));
                    user.seteMail(rs.getNString("userEmail"));
                    user.setFirstName(rs.getNString("fName"));
                    user.setLastName(rs.getNString("lName"));
                    user.setPhone(rs.getNString("phNumber"));
                    user.setRoleName(rs.getNString("roleName"));

                    cashier.setCashierId((Long) rs.getObject("cashierId"));
                    cashier.setCashPaymentAllowed(rs.getBoolean("CashPay"));
                    cashier.setCardNumber(rs.getNString("Card"));
                    cashier.setCardBank(rs.getNString("Bank"));
                    cashier.setCardNote(rs.getNString("Note"));
                    cashier.setCardQrCode(rs.getNString("QrCode"));

                    profile.setUserEntity(user);
                    profile.setCashierEntity(cashier);


                    return profile;
                }
            });

           return entityList;

    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean delete(ProfileEntity entity) {
        return false;
    }

    @Override
    public List<ProfileEntity> getAllByFilter(Object filter) {
        return null;
    }
}