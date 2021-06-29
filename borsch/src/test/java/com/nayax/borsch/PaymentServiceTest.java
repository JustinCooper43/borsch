package com.nayax.borsch;

import com.nayax.borsch.model.dto.ResponseDto;
import com.nayax.borsch.model.dto.order.request.ReqPayConfirmDto;
import com.nayax.borsch.service.impl.PaymentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
public class PaymentServiceTest {
    @Autowired
    PaymentService paymentService;

    @Test
    public void paymentConfirmationTest() {
        ReqPayConfirmDto request = new ReqPayConfirmDto();
        request.setPaid(new BigDecimal("23"));
        request.setUserId(7L);
        request.setOrderDate(LocalDate.of(2020, 10, 10));

        ResponseDto<Boolean> response = paymentService.confirmPayment(request);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getData());
        Assertions.assertTrue(response.getData());
    }
}
