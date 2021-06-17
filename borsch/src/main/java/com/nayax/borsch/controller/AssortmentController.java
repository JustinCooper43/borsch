package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.response.RespAssortmentDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping
    public ResponseEntity<ResponseDto<PageDto<RespAssortmentDto>>> getAssortment(@RequestParam int page, @RequestParam int pageSize) {
        RespAssortmentDto assortment = new RespAssortmentDto();
        assortment.setHalfable(true);
        assortment.setAdditions(getMockList());
        assortment.setRemarks(getMockList());
        assortment.setDish(getMockList().get(0));
        PageDto<RespAssortmentDto> pageDto = new PageDto<>(List.of(assortment, assortment, assortment, assortment, assortment, assortment, assortment, assortment));
        pageDto.setCurrentPageNumber(page);
        pageDto.setElementsPerPage(pageSize);
        pageDto.setTotalPages(23);
        pageDto.setTotalElements(23 * pageSize);
        ResponseDto<PageDto<RespAssortmentDto>> responseDto = new ResponseDto<>(pageDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<RespAssortmentDto>> getById(@RequestParam Long id) {
        RespAssortmentDto dto = new RespAssortmentDto();
        dto.setAdditions(getMockList());
        dto.setRemarks(getMockList());
        dto.setDish(getMockList().get(0));
        dto.setHalfable(true);
        ResponseDto<RespAssortmentDto> respDto = new ResponseDto<>(dto);
        return ResponseEntity.ok(respDto);
    }
}
