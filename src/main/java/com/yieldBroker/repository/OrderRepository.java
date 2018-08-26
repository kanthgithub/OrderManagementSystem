package com.yieldBroker.repository;

import com.yieldBroker.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * JPA Implementation for Orders table
 *
 * JPA API is used to find and delete operations
 *
 * Any custom Methods/implementations should exist in OrderCustomRepository
 */
public interface OrderRepository extends JpaRepository<Order, Long> , OrderCustomRepository {

    /**
     * extract All Orders - Sorted By Side and then ReceivedTime Descending
     *
     * @return List<Order>
     */
    public List<Order> findAllByOrderBySideAscReceivedTimeDesc();

    /**
     * find Order by clientOrderId as Key
     * @return
     */
    public Order findOneByClientOrderId(Integer clientOrderId);


    /**
     * delete Order - using clientOrderId as key
     * @param clientOrderId
     * @return primaryKey of the deleted Entity
     */
    @Transactional
    Long deleteByClientOrderId(Integer clientOrderId);

}