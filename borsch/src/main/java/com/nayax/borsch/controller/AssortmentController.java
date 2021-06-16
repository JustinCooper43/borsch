package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.response.RespAssortmentDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/assortment")
public class AssortmentController {

    private List<RespSimpleItemDto> getMockList() {
        RespSimpleItemDto item = new RespSimpleItemDto();
        item.setId(13L);
        item.setName("SimpleItem");
        return List.of(item, item, item, item, item, item, item, item, item, item);
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDto<List<RespAssortmentDto>>> getAssortment(@RequestParam int page, @RequestParam int pageSize) {
        RespAssortmentDto assortment = new RespAssortmentDto();
        assortment.setHalfable(true);
        assortment.setAdditions(getMockList());
        assortment.setRemarks(getMockList());
        assortment.setDish(getMockList().get(0));
        ResponseDto<List<RespAssortmentDto>> responseDto = new ResponseDto<>(
                List.of(assortment, assortment, assortment, assortment, assortment, assortment, assortment, assortment));
        return ResponseEntity.ok(responseDto);
    }


}
