package com.nayax.borsch.controller;


import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.response.RespAssortmentDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import com.nayax.borsch.model.dto.user.response.RespProfileDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assortment")
public class AssortmentController {

    @PostMapping("/post")
    public ResponseEntity<ResponseDto<RespAssortmentDto>> getById(@RequestParam Long id) {
        RespAssortmentDto dto = new RespAssortmentDto();
        dto.setAdditions(getMockList());
        dto.setRemarks(getMockList());
        dto.setDish(getMockList().get(0));
        dto.setHalfAble(true);
        ResponseDto<RespAssortmentDto> respDto = new ResponseDto<>(dto);
        return ResponseEntity.ok(respDto);
    }
}
