package com.nayax.borsch.service.impl;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemAddDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class DishService {


    @PostMapping
    public ResponseDto<RespSimplePriceItemDto> addDish(ReqSimplePriceItemAddDto dto){
    return null;
    }
}
