package com.nayax.borsch.repository.impl;

import com.nayax.borsch.exceptions.NotUpdateException;
import com.nayax.borsch.model.entity.user.CashierEntity;
import com.nayax.borsch.model.entity.user.ProfileEntity;
import com.nayax.borsch.model.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ProfileRepositoryImplementation {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParam;

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
        }, keyHolder);

        Long i = Objects.requireNonNull(keyHolder.getKey()).longValue();
        if (entity.getCashierEntity() != null) {
            entity.getCashierEntity().setUserId(i);
            String sqlCashier = "INSERT INTO Cashier " +
                    "(UserId, CashPaymentAllowed " +
                    ",CCNumber, CCBank, CCNote, CCQRCode)" +
                    " OUTPUT INSERTED.* VALUES (?, ?, ?, ?, ?, ?)";
            CashierEntity cashierEntity = jdbcTemplate.queryForObject(sqlCashier, new BeanPropertyRowMapper<>(CashierEntity.class),
                    entity.getCashierEntity().getUserId(),
                    entity.getCashierEntity().isCashPaymentAllowed(), entity.getCashierEntity().getCardNumber(),
                    entity.getCashierEntity().getCardBank(), entity.getCashierEntity().getCardNote(),
                    entity.getCashierEntity().getCardQrCode());
        }
        return findByUserId(i).orElseThrow(NotUpdateException::new);
    }


    public ProfileEntity update(ProfileEntity entity) throws NotUpdateException {
        String userUpd = " Declare @mainId bigint = ?; " +
                " UPDATE [User] " +
                " SET Email  = ?, FirstName  = ?, LastName = ?, PhoneNumber  = ? " +
                " WHERE id = @mainId; ";
        jdbcTemplate.update(userUpd, entity.getUserEntity().getId(),
                entity.getUserEntity().geteMail(), entity.getUserEntity().getFirstName(),
                entity.getUserEntity().getLastName(), entity.getUserEntity().getPhone());
        return findByUserId(entity.getUserEntity().getId()).orElseThrow(NotUpdateException::new);
    }


    public Optional<ProfileEntity> findByUserId(Long userId) {

        String sql = " SELECT u.id  userId, u.Active activeUser, u.RoleId roleId, " +
                " u.Email userEmail, u.FirstName fName, u.LastName lName, u.PhoneNumber phNumber, r.[Name] roleName, " +
                " c.id cashierId, c.CashPaymentAllowed CashPay, c.CCNumber Card, c.CCBank Bank, c.CCNote Note, c.CCQRCode QrCode " +
                " FROM [User] u " +
                " LEFT JOIN Cashier c ON u.id = c.UserId " +
                " LEFT JOIN  [Role] r on u.RoleId = r.id " +
                " WHERE u.id = ? AND u.Active = 'Y'; ";

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

                    cashier.setUserId((Long) rs.getObject("cashierId"));
                    cashier.setCashPaymentAllowed(rs.getBoolean("CashPay"));
                    cashier.setCardNumber(rs.getNString("Card"));
                    cashier.setCardBank(rs.getNString("Bank"));
                    cashier.setCardNote(rs.getNString("Note"));
                    cashier.setCardQrCode(rs.getNString("QrCode"));

                    profile.setUserEntity(user);
                    profile.setCashierEntity(cashier);

                    return profile;
                }
            }, userId);
            result = Optional.ofNullable(profileEntity);
        } catch (EmptyResultDataAccessException e) {

            System.err.printf("Profile %s not found\n", userId == null ? null : userId.toString());
        }
        return result;
    }


    public List<ProfileEntity> findAll() {
        String query = "SELECT u.id  userId, u.Active activeUser, u.RoleId roleId, \n" +
                " u.Email userEmail, u.FirstName fName, u.LastName lName, u.PhoneNumber phNumber, r.[Name] roleName, \n" +
                " c.id cashierId, c.CashPaymentAllowed CashPay, c.CCNumber Card, c.CCBank Bank, c.CCNote Note, c.CCQRCode QrCode\n" +
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

                cashier.setUserId((Long) rs.getObject("cashierId"));
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


    public ProfileEntity deleteByUserId(Long id) {
        Optional<ProfileEntity> entity = findByUserId(id);
        if (entity.isPresent()) {
            String query = "Update [User] SET Active = 'N' where id = ?";
            jdbcTemplate.update(query, id);
            return entity.get();
        }
        throw new NotUpdateException();
    }

    public Optional<Long> getCurrentCashierUserIdByEmail(String email) {
        String query = "SELECT [OrderSummary].[id] \n" +
                " ,[User].id userId \n" +
                " ,[User].Email \n" +
                "  FROM [OrderSummary] \n" +
                "  join [Cashier] on [Cashier].id = [OrderSummary].CashierId \n" +
                "  join [User] on [User].id = [Cashier].UserId \n" +
                "  where [User].Email like ?  and [OrderSummary].StopTime is null";
        Optional<Long> result = Optional.empty();
        try {
            Long id = jdbcTemplate.queryForObject(query, new RowMapper<Long>() {
                @Override
                public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return ((Long) rs.getObject("userId"));
                }
            }, email);
            result = Optional.ofNullable(id);
        } catch (EmptyResultDataAccessException e) {
            System.err.printf("Cashier Id by email  %s not found\n", email == null ? null : email.toString());
        }
        return result;
    }

    public Optional<ProfileEntity> findByEmail(String email) {
        String sql = " SELECT u.id  userId, u.Active activeUser, u.RoleId roleId, " +
                " u.Email userEmail, u.FirstName fName, u.LastName lName, " +
                " u.PhoneNumber phNumber, r.[Name] roleName, c.id cashierId " +
                " FROM [User] u " +
                " LEFT JOIN Cashier c ON u.id = c.UserId " +
                " LEFT JOIN  [Role] r on u.RoleId = r.id " +
                " WHERE u.Email LIKE ? AND u.Active = 'Y' ; ";
        try {
            List<ProfileEntity> profileEntity = jdbcTemplate.query(sql, new RowMapper<ProfileEntity>() {
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
                    cashier.setUserId((Long) rs.getObject("cashierId"));
                    profile.setUserEntity(user);
                    profile.setCashierEntity(cashier);
                    return profile;
                }
            }, email);
            return profileEntity.size() == 1 ? Optional.ofNullable(profileEntity.get(0)) : Optional.empty();
        } catch (EmptyResultDataAccessException e) {
            System.err.println("Profile for email " + email + " not found");
        }
        return Optional.empty();
    }

    public Optional<Long> getCurrentUserIdByEmail(String email) {
        String query = "SELECT id FROM [User] where email like ?";
        Optional<Long> result = Optional.empty();
        try {
            Long id = jdbcTemplate.queryForObject(query, new RowMapper<Long>() {
                @Override
                public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return ((Long) rs.getObject("id"));
                }
            }, email);
            result = Optional.ofNullable(id);
        } catch (EmptyResultDataAccessException e) {

            System.err.printf("Cashier Id by email  %s not found\n", email == null ? null : email.toString());
        }
        return result;
    }

    public Long latestOrderSummaryCashier() {
        String sql = " SELECT TOP (1) UserId " +
                " FROM Cashier " +
                " JOIN OrderSummary on OrderSummary.CashierId = Cashier.id " +
                " ORDER BY OrderSummary.StartTime DESC ;";
        return jdbcTemplate.queryForObject(sql, new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                return ((Long) rs.getObject("UserId"));
            }
        });
    }

    public ProfileEntity updateCurrentCashierInSumOrd(Long cashierId) {


        String sql = "UPDATE OrderSummary SET CashierId = ? where id =  (" +
                "SELECT TOP (1) OrderSummary.id id " +
                "FROM  OrderSummary " +
                "ORDER BY OrderSummary.StartTime DESC " +
                ");";


        try {
            jdbcTemplate.update(sql,
                    cashierId);
        } catch (EmptyResultDataAccessException e) {

        }
        return findByUserId(cashierId).orElse(null);
    }
}



