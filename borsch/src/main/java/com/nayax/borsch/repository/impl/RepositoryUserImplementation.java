package com.nayax.borsch.repository.impl;

import com.nayax.borsch.model.entity.user.UserEntity;
import com.nayax.borsch.repository.GenericCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
                "output inserted.* values  (?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(UserEntity.class),
                entity.getActive(), entity.getRoleId(),
                entity.geteMail(), entity.getFirstName(), entity.getLastName(),
                entity.getPhone()
        );
    }

    @Override
    public UserEntity update(UserEntity entity) {

        String sql = "UPDATE [User] SET  [User].Deleted = ? , " +
                "[User].RoleId = ? , [User].Email = ? , " +
                "[User].FirstName = ? , [User].LastName = ? , " +
                "[User].PhoneNumber = ?  where [User].id = ? ";
        int result = 0;
        try {
            result = jdbcTemplate.update(sql,
                    entity.getActive(), entity.getRoleId(), entity.geteMail(),
                    entity.getFirstName(), entity.getLastName(), entity.getPhone(), entity.getId());
//            TODO return type is boolean
//            return result == 1;
            return new UserEntity();
        } catch (EmptyResultDataAccessException e) {
            return new UserEntity();
        }
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        // TODO add isDeleted filter to where statement
        String sql = "SELECT [User].id  userId, [User].Deleted deletedUser, [User].RoleId roleId,\n" +
                "[User].Email userEmail, [User].FirstName fName, [User].LastName lName,\n" +
                "[User].PhoneNumber phNumber, [Role].[Name] roleName FROM [User]\n" +
                "JOIN  [Role] on [Role].id = [User].RoleId\n" +
                "WHERE [User].id = ?;";
        var result = Optional.<UserEntity>empty();
        try {
            UserEntity user = jdbcTemplate.queryForObject(sql, new RowMapper<UserEntity>() {
                @Override
                public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setId((Long) rs.getObject("userId"));
                    userEntity.setRoleId((Long) rs.getObject("roleId"));
                    userEntity.seteMail(rs.getNString("userEmail"));
                    userEntity.setFirstName(rs.getNString("fName"));
                    userEntity.setLastName(rs.getNString("lName"));
                    userEntity.setActive(rs.getString("deletedUser"));
                    userEntity.setPhone(rs.getNString("phNumber"));
                    userEntity.setRoleName(rs.getNString("roleName"));
                    return userEntity;
                }
            }, id);
            result = Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            // TODO perhaps needs logs
            System.err.printf("User %s not found\n", id == null ? null : id.toString());
        }
        return result;
    }


    @Override
    public List<UserEntity> findAll() {
        // TODO add isDeleted filter to where statement
        String sql = "SELECT \n" +
                "[User].id  userId, [User].Deleted deletedUser, [User].RoleId roleId,\n" +
                "[User].Email userEmail, [User].FirstName fName, [User].LastName lName,\n" +
                "[User].PhoneNumber phNumber FROM [User]";

        List<UserEntity> listUsers = new ArrayList<>();
        // jdbcTemplate.query doesn't throws EmptyResultDataAccessException
        listUsers = jdbcTemplate.query(sql, new RowMapper<UserEntity>() {
            @Override
            public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserEntity userEntity = new UserEntity();
                userEntity.setId((Long) rs.getObject("userId"));
                userEntity.setRoleId((Long) rs.getObject("roleId"));
                userEntity.seteMail(rs.getNString("userEmail"));
                userEntity.setFirstName(rs.getNString("fName"));
                userEntity.setLastName(rs.getNString("lName"));
                userEntity.setActive(rs.getString("deletedUser"));
                userEntity.setPhone(rs.getNString("phNumber"));
                return userEntity;
            }
        });
        return listUsers;

    }

    @Override
    public boolean delete(Long id) {

        String sql = "UPDATE [User] SET [User].Deleted = 'Y' WHERE id = ?";

        try {
            int rows = jdbcTemplate.update(sql, id);
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
    public List<UserEntity> getAllByFilter(Object filter) {
        return null;
    }
}
