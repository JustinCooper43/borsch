package com.nayax.borsch.example.handlers;


import com.nayax.borsch.model.dto.response.ErrorDto;
import com.nayax.borsch.model.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//@RestControllerAdvice
public class ControllerExceptionHandler {

    //@ExceptionHandler(SQLException.class)
    ResponseEntity<ResponseDto<?>> sqlExceptionHandler(SQLException e, WebRequest request) {
        List<ErrorDto> list = List.of(new ErrorDto());
        list.get(0).setCause("Invalid sql query");
        return ResponseEntity.ok(new ResponseDto<>(list));
    }
}
