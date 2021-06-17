package com.nayax.borsch.controller;


import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemAddDto;
import com.nayax.borsch.model.dto.assortment.request.ReqSimplePriceItemUpDto;
import com.nayax.borsch.model.dto.assortment.response.RespPriceItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.model.dto.order.request.ReqOrderItemAddDto;
import com.nayax.borsch.model.dto.order.response.RespOrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishEditorController {


    private RespSimplePriceItemDto getRespDishEditMock() {
        RespSimplePriceItemDto priceItemDto = new RespSimplePriceItemDto();
        priceItemDto.setId(1l);
        priceItemDto.setPrice(new BigDecimal("90.21"));
        priceItemDto.setName("Шаурма куриная");
        return priceItemDto;
    }

    @PostMapping("/")
    public ResponseEntity<ResponseDto<RespSimplePriceItemDto>> addDish(@RequestBody ReqSimplePriceItemAddDto dto) {
        RespSimplePriceItemDto priceItemDto = getRespDishEditMock();
        ResponseDto<RespSimplePriceItemDto> responseDto = new ResponseDto<>(priceItemDto);
        return ResponseEntity.ok(responseDto);
    }


    @GetMapping("/")
    public ResponseEntity<ResponseDto<List<RespSimplePriceItemDto>>> getDish( @RequestParam Long page, @RequestParam Long pageSize){
        RespSimplePriceItemDto priceItem = getRespDishEditMock();
        List <RespSimplePriceItemDto> priceItemList = List.of(priceItem,priceItem,priceItem,priceItem,priceItem,priceItem);
        ResponseDto <List<RespSimplePriceItemDto>> responseDto = new ResponseDto<>(priceItemList);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/")
    public ResponseEntity<ResponseDto<RespSimplePriceItemDto>> updateDish( @RequestBody ReqSimplePriceItemUpDto dto){
        RespSimplePriceItemDto priceItemDto = getRespDishEditMock();
        ResponseDto<RespSimplePriceItemDto> responseDto = new ResponseDto<>(priceItemDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/")
    public ResponseEntity<ResponseDto<Boolean>> deleteDish(@RequestParam Long dishId){
        ResponseDto<Boolean> responseDto = new ResponseDto<>(true);

        return ResponseEntity.ok(responseDto) ;
    }



}
