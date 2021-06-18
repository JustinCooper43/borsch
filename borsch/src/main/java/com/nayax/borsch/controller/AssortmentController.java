package com.nayax.borsch.controller;

import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.request.ReqAssortmentUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespAssortmentDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        PageDto<RespAssortmentDto> pageDto = PageDto.getPagedList(page, pageSize,
                List.of(assortment, assortment, assortment, assortment, assortment, assortment, assortment, assortment));
        ResponseDto<PageDto<RespAssortmentDto>> responseDto = new ResponseDto<>(pageDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<RespAssortmentDto>> getById(@PathVariable(value = "id") Long id, @RequestBody ReqAssortmentUpDto dto) {
        dto.setId(id);
        RespAssortmentDto rDto = new RespAssortmentDto();
        rDto.setAdditions(getMockList());
        rDto.setRemarks(getMockList());
        rDto.setDish(getMockList().get(0));
        rDto.setHalfable(true);
        ResponseDto<RespAssortmentDto> respDto = new ResponseDto<>(rDto);
        return ResponseEntity.ok(respDto);
    }

    @PutMapping("")
    public ResponseEntity<ResponseDto<RespAssortmentDto>> editAssortment(RespAssortmentDto dto) {
        RespAssortmentDto respAssortmentDto = new RespAssortmentDto();
        dto.setAdditions(getMockList());
        dto.setRemarks(getMockList());
        dto.setDish(getMockList().get(0));
        dto.setHalfable(true);
        return ResponseEntity.ok(new ResponseDto<>(respAssortmentDto));
    }
}
