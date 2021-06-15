package com.nayax.borsch.exampleselezniov.repository;

import com.nayax.borsch.exampleselezniov.model.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

@org.springframework.stereotype.Repository
public class RepositoryImpl implements Repository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Employee add(Employee entity) {
        System.out.println("####### Started repository.add #######");
        String sql = "INSERT INTO Employee (FirstName, LastName, Salary)" +
                "OUTPUT INSERTED.* VALUES(?, ?, ?);";
        System.out.println("####### Returning entity from repository.add #######");
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Employee.class),
                entity.getFirstName(), entity.getLastName(), entity.getSalary());
    }

    @Override
    public Optional<Employee> get(Long id) throws EmptyResultDataAccessException {
        System.out.println("####### Started repository.get #######");
        String sql = "SELECT yyyFirstName, LastName, Salary, Id FROM Employee WHERE id = ? ";
        try {
            Employee e = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                Employee employee = new Employee();
                employee.setId((Long) rs.getObject("Id"));
                employee.setFirstName(rs.getNString("FirstName"));
                employee.setLastName(rs.getNString("LastName"));
                employee.setSalary(rs.getBigDecimal("Salary"));
                return employee;
            }, id);
            System.out.println("####### Returning entity from repository.get #######");
            return Optional.ofNullable(e);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("####### Returning empty optional from repository.get #######");
            return Optional.empty();
        }
    }
}