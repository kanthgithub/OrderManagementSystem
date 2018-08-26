package com.yieldBroker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class OrderModel {

    @NotNull
    private Integer clientOrderId;

    @NotNull
    private String side;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer volume;

    @JsonIgnore
    private Date receivedTime;

    @JsonIgnore
    private Boolean isOperationSuccessful;

    @JsonIgnore
    private String message;

    public Integer getClientOrderId() {
        return clientOrderId;
    }

    public OrderModel setClientOrderId(Integer clientOrderId) {
        this.clientOrderId = clientOrderId;
        return this;
    }

    public String getSide() {
        return side;
    }

    public OrderModel setSide(String side) {
        this.side = side;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getVolume() {
        return volume;
    }

    public OrderModel setVolume(Integer volume) {
        this.volume = volume;
        return this;
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public OrderModel setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
        return this;
    }

    public Boolean getOperationSuccessful() {
        return isOperationSuccessful;
    }

    public OrderModel setOperationSuccessful(Boolean operationSuccessful) {
        isOperationSuccessful = operationSuccessful;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public OrderModel setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderModel that = (OrderModel) o;
        return Objects.equals(clientOrderId, that.clientOrderId) &&
                Objects.equals(side, that.side) &&
                Objects.equals(price, that.price) &&
                Objects.equals(volume, that.volume) &&
                Objects.equals(receivedTime, that.receivedTime) &&
                Objects.equals(isOperationSuccessful, that.isOperationSuccessful) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {

        return Objects.hash(clientOrderId, side, price, volume, receivedTime, isOperationSuccessful, message);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderModel{");
        sb.append("clientOrderId=").append(clientOrderId);
        sb.append(", side='").append(side).append('\'');
        sb.append(", price=").append(price);
        sb.append(", volume=").append(volume);
        sb.append(", receivedTime=").append(receivedTime);
        sb.append(", isOperationSuccessful=").append(isOperationSuccessful);
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
