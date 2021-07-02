package com.nayax.borsch.controller;


import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.response.RespAssortmentItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespPriceItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.service.impl.OrderEditorService;
import com.nayax.borsch.utility.enums.ErrorStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class OrderEditorController {

    @Autowired
    OrderEditorService orderEditorService;

    @GetMapping("/price")
    public ResponseEntity<ResponseDto<List<RespPriceItemDto>>> price() {

        return ResponseEntity.ok(new ResponseDto<>(orderEditorService.price()).setStatus(ErrorStatus.OK.statusName));
    }

    @GetMapping("/dish/dropdown")
    public ResponseEntity<ResponseDto<List<RespAssortmentItemDto>>> dishDropdown() {
        return ResponseEntity.ok(new ResponseDto<>(orderEditorService.dish()).setStatus(ErrorStatus.OK.statusName));
    }

    @GetMapping("/additional/dropdown")
    public ResponseEntity<ResponseDto<List<RespSimplePriceItemDto>>> additionalDropdown(@RequestParam Long dishId) {
        return ResponseEntity.ok(orderEditorService.additionalDropdown(dishId).setStatus(ErrorStatus.OK.statusName));
    }

    @GetMapping("/drink/dropdown")
    public ResponseEntity<ResponseDto<List<RespSimplePriceItemDto>>> drinkDropdown() {
        return ResponseEntity.ok(new ResponseDto<>(orderEditorService.drinks()).setStatus(ErrorStatus.OK.statusName));
    }

    @GetMapping("/remark/dropdown")
    public ResponseEntity<ResponseDto<List<RespSimpleItemDto>>> remarkDropdown() {
        return ResponseEntity.ok(new ResponseDto<>(orderEditorService.remarks()).setStatus(ErrorStatus.OK.statusName));
    }
}
