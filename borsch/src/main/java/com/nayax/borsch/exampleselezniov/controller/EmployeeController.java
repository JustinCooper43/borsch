package com.nayax.borsch.exampleselezniov.controller;

import com.nayax.borsch.exampleselezniov.model.dto.EmployeeAddDto;
import com.nayax.borsch.exampleselezniov.model.dto.EmployeeResponseDto;
import com.nayax.borsch.exampleselezniov.model.dto.ResponseDto;
import com.nayax.borsch.exampleselezniov.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RestController
public class EmployeeController {
  //  @Autowired
    private Service service;

    //@PostMapping(value = "/add")
    public ResponseEntity<ResponseDto<EmployeeResponseDto>> add(@RequestBody EmployeeAddDto dto) {
        System.out.println("####### Starting controller.add #######");
        ResponseDto<EmployeeResponseDto> response = service.add(dto);
        System.out.println("####### Returning from controller.add #######");
        return ResponseEntity.ok(response);
    }

    //@GetMapping(value = "/get")
    public ResponseEntity<ResponseDto<EmployeeResponseDto>> get(@RequestParam Long id) {
        System.out.println("####### Starting controller.get #######");
        ResponseDto<EmployeeResponseDto> response = service.get(id);
        System.out.println("####### Returning from controller.get #######");
        return ResponseEntity.ok(response);
    }
}
