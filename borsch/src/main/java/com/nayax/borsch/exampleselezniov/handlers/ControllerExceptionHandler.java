package com.nayax.borsch.exampleselezniov.handlers;

import com.nayax.borsch.exampleselezniov.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.util.List;

//@RestControllerAdvice
public class ControllerExceptionHandler {
    //@ExceptionHandler(SQLException.class)
    public ResponseEntity<ResponseDto<?>> sqlExceptionHandler(SQLException e, WebRequest request) {
        List<ErrorDto> error = List.of(new ErrorDto());
        error.get(0).setMessage(e.getMessage());
        return ResponseEntity.ok(new ResponseDto<>(error));
    }
}
