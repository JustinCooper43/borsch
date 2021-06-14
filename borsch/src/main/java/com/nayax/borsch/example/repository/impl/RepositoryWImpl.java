package com.nayax.borsch.example.repository.impl;


import com.nayax.borsch.example.model.Employee;
import com.nayax.borsch.example.model.dto.EmployeeAddDto;
import com.nayax.borsch.example.repository.RepositoryW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


@Repository
public class RepositoryWImpl implements RepositoryW {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public Employee add(Employee entity) {
        System.out.println("Repository method add start");
        String query = "Insert into Employee (FirstNAme, LastName, Salary) OUTPUT INSERTED.FirstName fname, INSERTED.lastName lname, INSERTED.Salary salary, INSERTED.id id values (?,?,?)";


        Employee emp = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Employee.class),  entity.getFName(), entity.getLName(), entity.getSalary());
        System.out.println("Repository method add end");
        return emp;
    }

    @Override
    public Optional<Employee> get(Long index) {

        System.out.println("Repository method get start");
        String query = "Select * from Employee where id =?";
        Optional<Employee> employee = Optional.empty();
        try {
            Employee emp = jdbcTemplate.queryForObject(query, new RowMapper<Employee>() {
                @Override
                public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Employee employee = new Employee();
                    employee.setId(rs.getLong("id"));
                    employee.setFName(rs.getNString("FirstName"));
                    employee.setLName(rs.getNString("LastName"));
                    employee.setSalary(rs.getBigDecimal("salary"));
                    return employee;
                }
            }, index);
            employee = Optional.ofNullable(emp);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("EmptyResultDataAccessException catched");
        }

        System.out.println("Repository method get end");
        return employee;
    }
}
