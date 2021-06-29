package com.nayax.borsch.controller.testController;

import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimpleItemDto;
import com.nayax.borsch.model.dto.assortment.response.RespSimplePriceItemDto;
import com.nayax.borsch.model.dto.order.response.RespOrderItemDto;
import com.nayax.borsch.repository.impl.OrderItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/order/item/page")
public class TestControllerPageOrderItem {

    @Autowired
    OrderItemRepo orderItemRepo;

    @GetMapping("/get")
    public ResponseEntity<ResponseDto<PageDto<RespOrderItemDto>>> getPagedOrders(
            @RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam Long userId, @RequestParam(required = false) String dateTime) {
        RespOrderItemDto orderItem = getRespOrderMock();

        LocalDate date = LocalDate.parse(dateTime);
        orderItemRepo.getListOrders(userId, date);

        List<RespOrderItemDto> itemList = List.of(orderItem, orderItem, orderItem, orderItem, orderItem, orderItem, orderItem);
        PageDto<RespOrderItemDto> pageDto = PageDto.getPagedList(page, pageSize, itemList);
        ResponseDto<PageDto<RespOrderItemDto>> responseDto = new ResponseDto<>(pageDto);
        return ResponseEntity.ok(responseDto);
    }

    private RespOrderItemDto getRespOrderMock() {
        RespOrderItemDto orderItem = new RespOrderItemDto();

        RespSimplePriceItemDto shawa = new RespSimplePriceItemDto();
        shawa.setId(2l);
        shawa.setName("Щаурма с курицей");
        shawa.setPrice(new BigDecimal("90.00"));
        orderItem.setDish(shawa);

        RespSimplePriceItemDto addition = new RespSimplePriceItemDto();
        addition.setId(4l);
        addition.setName("Картошка");
        addition.setPrice(new BigDecimal("10.00"));
        List<RespSimplePriceItemDto> respSimpleItemDtos = List.of(addition, addition, addition, addition, addition, addition, addition);
        orderItem.setAdditions(respSimpleItemDtos);

        RespSimplePriceItemDto drink = new RespSimplePriceItemDto();
        addition.setId(4l);
        addition.setName("Coca-cola");
        addition.setPrice(new BigDecimal("23.50"));
        orderItem.setDrink(drink);

        RespSimpleItemDto remark = new RespSimpleItemDto();
        remark.setId(1l);
        remark.setName("побольше соуса");
        orderItem.setRemark(remark);
        orderItem.setCut(false);
        orderItem.setQuantity(2);

        return orderItem;
    }
}
