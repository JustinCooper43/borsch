package com.nayax.borsch.controller;


import com.nayax.borsch.mapper.AssortmentMapper;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.response.RespAssortmentItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespPriceItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import com.nayax.borsch.model.entity.assortment.ShawarmaItemEntity;
import com.nayax.borsch.repository.impl.RepositoryShawarmaTypeImpl;
import com.nayax.borsch.repository.impl.TablesType;
import com.nayax.borsch.service.impl.OrderEditorService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class OrderEditorController {

    @Autowired
    OrderEditorService orderEditorService;


    @GetMapping("/price")
    public ResponseEntity<ResponseDto<List<RespPriceItemDto>>> price() {

        return ResponseEntity.ok(new ResponseDto<>(orderEditorService.price()));
    }

    @GetMapping("/dish/dropdown")
    public ResponseEntity<ResponseDto<List<RespAssortmentItemDto>>> dishDropdown() {
        return ResponseEntity.ok(new ResponseDto<>(orderEditorService.dish()));
    }

    @GetMapping("/additional/dropdown")
    public ResponseEntity<ResponseDto<List<RespSimplePriceItemDto>>> additionalDropdown(@RequestParam Long dishId) {

        return ResponseEntity.ok(null);
    }

    @GetMapping("/drink/dropdown")
    public ResponseEntity<ResponseDto<List<RespSimplePriceItemDto>>> drinkDropdown() {
        return ResponseEntity.ok(new ResponseDto<>(orderEditorService.drinks()));
    }

    @GetMapping("/remark/dropdown")
    public ResponseEntity<ResponseDto<List<RespSimpleItemDto>>> remarkDropdown() {
        return ResponseEntity.ok(new ResponseDto<>(orderEditorService.remarks()));
    }
}
