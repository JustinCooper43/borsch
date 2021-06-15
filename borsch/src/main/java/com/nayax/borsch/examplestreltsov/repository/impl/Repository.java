package com.nayax.borsch.examplestreltsov.repository.impl;

import com.nayax.borsch.examplestreltsov.models.entity.Employee;
import com.nayax.borsch.examplestreltsov.repository.RepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

@org.springframework.stereotype.Repository
public class Repository implements RepositoryInterface {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Employee add(Employee entity) {

        String sql = "insert into employee (FirstName, LastName, Salary) " +
                "ounput inserted.* values (?, ?, ?)";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Employee.class),
                entity.getFirstName(), entity.getLastName(), entity.getSalary());
    }

    @Override
    public Optional<Employee> get(Long id) {

        String sql = "selec FirstName, LastName, Salary, Id from employee where id = ?";
        try{
            Employee e = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                        Employee employee = new Employee();
                        employee.setId((Long) rs.getObject("Id"));
                        employee.setFirstName(rs.getNString("FirstName"));
                        employee.setLastName(rs.getNString("LastName"));
                        employee.setSalary(rs.getBigDecimal("Salary"));
                        return employee;
                    }
                    ,id);

            return Optional.ofNullable(e);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }
}
