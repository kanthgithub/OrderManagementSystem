package com.yieldBroker.service;

import com.yieldBroker.model.OrderBook;
import com.yieldBroker.model.OrderModel;

public interface OrderService {

    /**
     *
     * @return OrderBook
     */
     OrderBook getOrderBook();

    /**
     *
     * @param orderModel
     * @return OrderModel
     */
     OrderModel storeOrder(OrderModel orderModel);

    /**
     *
     * @param orderModel
     * @return OrderModel
     */
    OrderModel cancelOrder(OrderModel orderModel);
}
