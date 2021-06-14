package com.nayax.borsch.example.controller;


import com.nayax.borsch.example.model.dto.EmployeeAddDto;
import com.nayax.borsch.example.model.EmployeeResponseDto;
import com.nayax.borsch.example.model.response.ResponseDto;
import com.nayax.borsch.example.service.ServiceW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    @Autowired
    ServiceW service;

    @PostMapping("/add")
    public ResponseEntity<ResponseDto<EmployeeResponseDto>> addEmployee(@RequestBody EmployeeAddDto employee){
        System.out.println("service method service.add(employee) start");
        ResponseDto<EmployeeResponseDto> employeeAddDto = service.add(employee);
        System.out.println("service method service.add(employee) end");
        return ResponseEntity.ok(employeeAddDto);
    }

    @GetMapping("/get")
    public  ResponseEntity<ResponseDto<EmployeeResponseDto>> getEmployee(@RequestParam Long id ){
        System.out.println("service method service.get(id) start");
        ResponseDto<EmployeeResponseDto> employeeAddDto = service.get(id);
        System.out.println("service method service.get(id) end");
        return ResponseEntity.ok(employeeAddDto);
    }
}
