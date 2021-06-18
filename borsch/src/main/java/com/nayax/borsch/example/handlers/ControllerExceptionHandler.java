package com.nayax.borsch.example.handlers;


import com.nayax.borsch.model.dto.ErrorDto;
import com.nayax.borsch.model.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.util.List;

//@RestControllerAdvice
public class ControllerExceptionHandler {

    //@ExceptionHandler(SQLException.class)
    ResponseEntity<ResponseDto<?>> sqlExceptionHandler(SQLException e, WebRequest request) {
        List<ErrorDto> list = List.of(new ErrorDto());
        list.get(0).setMessage("Invalid sql query");
        return ResponseEntity.ok(new ResponseDto<>(list));
    }
}
