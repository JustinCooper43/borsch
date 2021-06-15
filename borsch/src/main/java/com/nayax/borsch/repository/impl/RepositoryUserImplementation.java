package com.nayax.borsch.repository.impl;

import com.nayax.borsch.model.entity.UserEntity;
import com.nayax.borsch.repository.GenericCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class RepositoryUserImplementation implements GenericCrudRepository<UserEntity, Object> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserEntity add(UserEntity entity) {

        String sql = "INSERT INTO [User] \n" +
                " ( [User].Deleted, \n" +
                "[User].RoleId , \n" +
                "[User].Email , [User].FirstName , \n" +
                "[User].LastName , \n" +
                "[User].PhoneNumber)\n" +
                "output inserted.* values  (?, ?, ?, ?. ?, ?, ?)";

        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserEntity.class),
                entity.getDeleted(), entity.getRoleId(),
                entity.geteMail(), entity.getFirstName(), entity.getLastName(),
                entity.getPhoneNumber()
        );
    }

    @Override
    public UserEntity update(UserEntity entity) {
        return null;
    }

    @Override
    public Optional<UserEntity> findById(Long id) {

        String sql = "SELECT [User].id  userId, [User].Deleted deletedUser, [User].RoleId roleId,\n" +
                "[User].Email userEmail, [User].FirstName fName, [User].LastName lName,\n" +
                "[User].PhoneNumber phNumber, [Role].[Name] roleName FROM [User]\n" +
                "JOIN  [Role] on [Role].id = [User].RoleId\n" +
                "WHERE [User].id = ?;";

        try {
            UserEntity user = jdbcTemplate.queryForObject(sql, new RowMapper<UserEntity>() {
                @Override
                public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setUserId((Long) rs.getObject(" userId"));
                    userEntity.setRoleId((Long) rs.getObject("roleId"));
                    userEntity.seteMail(rs.getNString("userEmail"));
                    userEntity.setFirstName(rs.getNString("fName"));
                    userEntity.setLastName(rs.getNString("lName"));
                    userEntity.setDeleted((Character) rs.getObject("deletedUser"));
                    userEntity.setPhoneNumber(rs.getNString("phNumber"));
                    userEntity.setRoleName(rs.getNString(" roleName"));
                    return userEntity;
                }
            }, id);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    @Override
    public List<UserEntity> findAll() {
        return null;
    }

    @Override
    public boolean delete(Long id) {

        String sql = "DELETE FROM [User] WHERE id = ?";

        try {
            int rows = jdbcTemplate.update(sql, id, Types.BIGINT);
            return rows > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean delete(UserEntity entity) {
        return false;
    }

    @Override
    public List<? extends UserEntity> getAllByFilter(Object filter) {
        return null;
    }
}
