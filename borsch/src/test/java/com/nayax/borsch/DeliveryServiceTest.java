package com.nayax.borsch;

import com.nayax.borsch.model.dto.PageDto;
import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.order.response.RespOrderDeliveryDto;
import com.nayax.borsch.service.impl.DeliveryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class DeliveryServiceTest {
    @Autowired
    DeliveryService deliveryService;

    @Test
    public void dummyDeliverySummaryTest() {
        ResponseDto<PageDto<RespOrderDeliveryDto>> response = deliveryService.getPagedDeliveryInfo(1, 5, LocalDateTime.now());

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getData());
        Assertions.assertNotNull(response.getData().getResponseList());
        Assertions.assertNotNull(response.getData().getResponseList().get(0));
    }
}
