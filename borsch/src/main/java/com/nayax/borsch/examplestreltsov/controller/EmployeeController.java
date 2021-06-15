package com.nayax.borsch.examplestreltsov.controller;

import com.nayax.borsch.examplestreltsov.models.dto.EmployeeAddDto;
import com.nayax.borsch.examplestreltsov.models.dto.EmployeeResponseDto;
import com.nayax.borsch.examplestreltsov.service.impl.Service;
import com.nayax.borsch.model.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RestController
public class EmployeeController {

//    @Autowired
    Service service;

//    @PostMapping("/add")
    public ResponseEntity<ResponseDto<EmployeeResponseDto>> add(@RequestBody EmployeeAddDto addDto) {
        ResponseDto<EmployeeResponseDto> response = service.add(addDto);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/get")
    public ResponseEntity<ResponseDto<EmployeeResponseDto>> get(@RequestParam Long id) {
        ResponseDto<EmployeeResponseDto> response = service.get(id);
        return ResponseEntity.ok(response);
    }

}
