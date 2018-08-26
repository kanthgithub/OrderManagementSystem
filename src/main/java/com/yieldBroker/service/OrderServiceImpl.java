package com.yieldBroker.service;

import com.yieldBroker.entity.Order;
import com.yieldBroker.model.OrderBook;
import com.yieldBroker.model.OrderBookModel;
import com.yieldBroker.model.OrderModel;
import com.yieldBroker.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.yieldBroker.common.Side.BUY;
import static com.yieldBroker.common.Side.SELL;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     *
     * @return OrderBook
     */
    @Override
    public OrderBook getOrderBook() {
        List<Order> orderCollection = orderRepository.findAllByOrderBySideAscReceivedTimeDesc();

        List<OrderBookModel> buyOrders =
                getBuyOrdersForOrderBook(orderCollection).parallelStream()
                                                            .map(ybOrders -> convertToOrderBookModel(ybOrders))
                                                                .collect(Collectors.toList());

        List<OrderBookModel> sellOrders =
                getSellOrdersForOrderBook(orderCollection).parallelStream()
                                            .map(ybOrders -> convertToOrderBookModel(ybOrders))
                                                        .collect(Collectors.toList());

        return new OrderBook().setBuyOrders(buyOrders).setSellOrders(sellOrders);
    }


    /**
     *
     * @param orderList
     * @return List<Order>
     */
    public List<Order> getBuyOrdersForOrderBook(List<Order> orderList) {
        return orderList.parallelStream()
                .filter(ybOrders -> ybOrders.getSide().equalsIgnoreCase(BUY.getCode()))
                .sorted(Comparator.comparing(Order::getPrice).reversed().thenComparing(Order::getReceivedTime))
                .collect(Collectors.toList());
    }


    /**
     *
     * @param orderList
     * @return List<Order>
     */
    public List<Order> getSellOrdersForOrderBook(List<Order> orderList) {
        return orderList.parallelStream()
                .filter(ybOrders -> ybOrders.getSide().equalsIgnoreCase(SELL.getCode()))
                .sorted(Comparator.comparing(Order::getPrice).thenComparing(Order::getReceivedTime))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param orderModel
     * @return OrderModel
     */
    @Override
    public OrderModel storeOrder(OrderModel orderModel) {
        Order order = convertToEntity(orderModel);
        order.setReceivedTime(new Date());
        Order orderSaved = orderRepository.save(order);
        return orderSaved !=null ?
                convertToModel(orderSaved).setOperationSuccessful(Boolean.TRUE) :
                orderModel.setOperationSuccessful(Boolean.FALSE)
                        .setMessage(String.format("Place Order - Operation Failed for ClientOrderId: %s",
                                orderModel.getClientOrderId()));
    }

    /**
     * @param orderModel
     * @return OrderModel
     */
    @Override
    public OrderModel cancelOrder(OrderModel orderModel) {
        Long id = orderRepository.deleteByClientOrderId(orderModel.getClientOrderId());
        return id!=null ? orderModel.setOperationSuccessful(Boolean.TRUE)
                         : orderModel.setOperationSuccessful(Boolean.FALSE)
                            .setMessage(String.format("Cancel Order - Operation Failed for ClientOrderId: %s",
                                                                            orderModel.getClientOrderId()));
    }

    /**
     *
     * @param orderModel
     * @return Order
     * @throws ParseException
     */
    private Order convertToEntity(OrderModel orderModel) throws ParseException {
        return modelMapper.map(orderModel, Order.class).setId(null);
    }

    /**
     *
     * @param order
     * @return OrderModel
     * @throws ParseException
     */
    private OrderModel convertToModel(Order order) throws ParseException {
        return modelMapper.map(order, OrderModel.class);
    }


    /**
     *
     * @param order
     * @return OrderBookModel
     * @throws ParseException
     */
    private OrderBookModel convertToOrderBookModel(Order order) throws ParseException {
        return modelMapper.map(order, OrderBookModel.class);
    }
}
