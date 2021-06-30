package com.nayax.borsch;

import com.nayax.borsch.model.entity.order.OrderEntity;
import com.nayax.borsch.model.entity.order.OrderSumTimerEntity;
import com.nayax.borsch.repository.impl.DeliverySummaryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class DeliverySummaryRepoTest {
    @Autowired
    DeliverySummaryRepository deliverySummaryRepository;

    @Test
    public void dummySummaryTest() {
        List<OrderEntity> response = deliverySummaryRepository.getByOrderSummaryId(3L);
        Assertions.assertNotEquals(0, response.size());
    }

    @Test
    public void dummyTimerTest() {
        OrderSumTimerEntity response = deliverySummaryRepository.getTimerBeforeDate(LocalDate.now());
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getCashier());
    }
}
