package com.nayax.borsch.example.handlers;


import com.nayax.borsch.model.dto.response.ErrorDto;
import com.nayax.borsch.model.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(SQLException.class)
    ResponseEntity<ResponseDto<?>> sqlExceptionHandler(SQLException e, WebRequest request){

        ErrorDto errorDto = new ErrorDto();
        errorDto.setCause("SQL problem 500");
        ResponseDto<?> listResponseDto = new ResponseDto<>();
        listResponseDto.setErrors(List.of(errorDto));
        return ResponseEntity.ok(listResponseDto);
    }
}
