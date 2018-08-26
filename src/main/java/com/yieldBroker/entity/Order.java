package com.yieldBroker.entity;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "YB_ORDER")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "client_order_id", nullable = false)
    private Integer clientOrderId;

    @Column(name = "side", nullable = false)
    private String side;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "volume", nullable = false)
    private Integer volume;

    @Column(name = "received_time", nullable = false)
    private Date receivedTime	;


    public Long getId() {
        return id;
    }

    public Order setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getClientOrderId() {
        return clientOrderId;
    }

    public Order setClientOrderId(Integer clientOrderId) {
        this.clientOrderId = clientOrderId;
        return this;
    }

    public String getSide() {
        return side;
    }

    public Order setSide(String side) {
        this.side = side;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Order setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getVolume() {
        return volume;
    }

    public Order setVolume(Integer volume) {
        this.volume = volume;
        return this;
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public Order setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
        return this;
    }
}
