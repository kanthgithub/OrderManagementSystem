package com.yieldBroker.service;

import com.yieldBroker.common.Side;
import com.yieldBroker.model.OrderBook;
import com.yieldBroker.model.OrderBookModel;
import com.yieldBroker.model.OrderModel;
import com.yieldBroker.repository.OrderRepository;
import org.hamcrest.collection.IsIterableWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void test_Store_Order() {

        //given
         Integer clientOrderId = 11222;
        String side = Side.BUY.getCode();
        BigDecimal price = BigDecimal.valueOf(12.11).setScale(2, BigDecimal.ROUND_HALF_UP);
        Integer volume = 2000;

        OrderModel order_Input = getAnOrderModel(clientOrderId, side, price, volume);

        //when
        OrderModel orderSaved = orderService.storeOrder(order_Input);

        //then
        assertNotNull(orderSaved);
        assertEquals(order_Input.getClientOrderId(), orderSaved.getClientOrderId());
        assertEquals(order_Input.getSide(), orderSaved.getSide());
        assertEquals(order_Input.getVolume(), orderSaved.getVolume());
        assertEquals(order_Input.getPrice(), orderSaved.getPrice());
    }

    @Test
    public void test_Cancel_Order() {

        //given
        Integer clientOrderId = 11222;
        String side = Side.BUY.getCode();
        BigDecimal price = BigDecimal.valueOf(12.11).setScale(2, BigDecimal.ROUND_HALF_UP);
        Integer volume = 2000;

        OrderModel order_Input = getAnOrderModel(clientOrderId, side, price, volume);

        OrderModel orderSaved = orderService.storeOrder(order_Input);

        //when
        OrderModel orderModel_Cancel_Response = orderService.cancelOrder(orderSaved);

        //then
        assertNotNull(orderModel_Cancel_Response);
        assertTrue(orderModel_Cancel_Response.getOperationSuccessful());
        assertNull(orderModel_Cancel_Response.getMessage());
        assertNull(orderRepository.findOneByClientOrderId(clientOrderId));
    }

    @Test
    public void test_Get_Order_Book(){

        //given
        Integer clientOrderId_Buy_1 = 11222;
        String side_Buy_1 = Side.BUY.getCode();
        BigDecimal price_Buy_1 = BigDecimal.valueOf(12.11).setScale(2, BigDecimal.ROUND_HALF_UP);
        Integer volume_Buy_1 = 2000;

        OrderModel order_Input_Buy_1 = getAnOrderModel(clientOrderId_Buy_1, side_Buy_1, price_Buy_1, volume_Buy_1);

        orderService.storeOrder(order_Input_Buy_1);

        Integer clientOrderId_Buy_2 = 22122;
        String side_Buy_2 = Side.BUY.getCode();
        BigDecimal price_Buy_2 = BigDecimal.valueOf(12.11).setScale(2, BigDecimal.ROUND_HALF_UP);
        Integer volume_Buy_2 = 2000;

        OrderModel order_Input_Buy_2 = getAnOrderModel(clientOrderId_Buy_2, side_Buy_2, price_Buy_2, volume_Buy_2);

        orderService.storeOrder(order_Input_Buy_2);


        Integer clientOrderId_Buy_3 = 32122;
        String side_Buy_3 = Side.BUY.getCode();
        BigDecimal price_Buy_3 = BigDecimal.valueOf(99.11).setScale(2, BigDecimal.ROUND_HALF_UP);
        Integer volume_Buy_3 = 2000;

        OrderModel order_Input_Buy_3 = getAnOrderModel(clientOrderId_Buy_3, side_Buy_3, price_Buy_3, volume_Buy_3);

        orderService.storeOrder(order_Input_Buy_3);

        Integer clientOrderId_Sell_1 = 71122;
        String side_Sell_1 = Side.SELL.getCode();
        BigDecimal price_Sell_1 = BigDecimal.valueOf(1).setScale(2, BigDecimal.ROUND_HALF_UP);
        Integer volume_Sell_1 = 2000;

        OrderModel order_Input_Sell_1 = getAnOrderModel(clientOrderId_Sell_1, side_Sell_1, price_Sell_1, volume_Sell_1);

        orderService.storeOrder(order_Input_Sell_1);

        Integer clientOrderId_Sell_2 = 72122;
        String side_Sell_2 = Side.SELL.getCode();
        BigDecimal price_Sell_2 = BigDecimal.valueOf(0.98).setScale(2, BigDecimal.ROUND_HALF_UP);
        Integer volume_Sell_2 = 2000;

        OrderModel order_Input_Sell_2 = getAnOrderModel(clientOrderId_Sell_2, side_Sell_2, price_Sell_2, volume_Sell_2);

        orderService.storeOrder(order_Input_Sell_2);

        //when
        OrderBook orderBook_Actual = orderService.getOrderBook();

        //then
        assertNotNull(orderBook_Actual);
        assertNotNull(orderBook_Actual.getBuyOrders());
        assertNotNull(orderBook_Actual.getSellOrders());
        assertThat(orderBook_Actual.getBuyOrders(),IsIterableWithSize.iterableWithSize(3));
        assertThat(orderBook_Actual.getSellOrders(),IsIterableWithSize.iterableWithSize(2));

        assertThat(orderBook_Actual.getBuyOrders(),containsInAnyOrder(
               new OrderBookModel().setPrice(price_Buy_3).setVolume(volume_Buy_3),
                new OrderBookModel().setPrice(price_Buy_1).setVolume(volume_Buy_1),
                new OrderBookModel().setPrice(price_Buy_2).setVolume(volume_Buy_2)
                )
        );

        assertThat(orderBook_Actual.getSellOrders(),containsInAnyOrder(
                new OrderBookModel().setPrice(price_Sell_2).setVolume(volume_Sell_2),
                new OrderBookModel().setPrice(price_Sell_1).setVolume(volume_Sell_1)
                )
        );
    }

    private OrderModel getAnOrderModel(Integer clientOrderId, String side, BigDecimal price, Integer volume) {
        return new OrderModel().setClientOrderId(clientOrderId).setSide(side)
                .setPrice(price).setVolume(volume);
    }
}
