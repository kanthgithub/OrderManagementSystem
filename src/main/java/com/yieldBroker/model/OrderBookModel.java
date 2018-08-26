package com.yieldBroker.model;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderBookModel {

    private BigDecimal price;

    private Integer volume;

    public BigDecimal getPrice() {
        return price;
    }

    public OrderBookModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getVolume() {
        return volume;
    }

    public OrderBookModel setVolume(Integer volume) {
        this.volume = volume;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderBookModel that = (OrderBookModel) o;
        return Objects.equals(price, that.price) &&
                Objects.equals(volume, that.volume);
    }

    @Override
    public int hashCode() {

        return Objects.hash(price, volume);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderBookModel{");
        sb.append("price=").append(price);
        sb.append(", volume=").append(volume);
        sb.append('}');
        return sb.toString();
    }
}
