package com.yieldBroker.repository;


import com.yieldBroker.common.Side;
import com.yieldBroker.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderRepositoryImplTest {


    @Autowired
    private OrderRepository orderRepository;


    @Test
    public void should_Store_Order_Successfully() {

        //given
        Integer clientOrderId = 11222;
        String side = Side.BUY.getCode();
        BigDecimal price = BigDecimal.valueOf(12.11).setScale(2, BigDecimal.ROUND_HALF_UP);
        Integer volume = 2000;

        Order order_Input = getAnOrderEntity(clientOrderId, side, price, volume);

        //when
        Order orderSaved = orderRepository.save(order_Input);

        //then
        assertNotNull(orderSaved);
        assertNotNull(orderSaved.getId());
        assertEquals(order_Input.getClientOrderId(), orderSaved.getClientOrderId());
        assertEquals(order_Input.getSide(), orderSaved.getSide());
        assertEquals(order_Input.getVolume(), orderSaved.getVolume());
        assertEquals(order_Input.getPrice(), orderSaved.getPrice());
    }

    private Order getAnOrderEntity(Integer clientOrderId, String side, BigDecimal price, Integer volume) {
        return new Order().setClientOrderId(clientOrderId).setSide(side)
                .setPrice(price).setVolume(volume).setReceivedTime(new Date());
    }
}
