package com.yieldBroker.model;

import java.util.List;

public class OrderBook {

    private List<OrderBookModel> buyOrders;

    private List<OrderBookModel> sellOrders;

    public List<OrderBookModel> getBuyOrders() {
        return buyOrders;
    }

    public OrderBook setBuyOrders(List<OrderBookModel> buyOrders) {
        this.buyOrders = buyOrders;
        return this;
    }

    public List<OrderBookModel> getSellOrders() {
        return sellOrders;
    }

    public OrderBook setSellOrders(List<OrderBookModel> sellOrders) {
        this.sellOrders = sellOrders;
        return this;
    }
}
