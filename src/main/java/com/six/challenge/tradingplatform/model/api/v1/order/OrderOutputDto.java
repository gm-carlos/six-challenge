package com.six.challenge.tradingplatform.model.api.v1.order;

import com.six.challenge.tradingplatform.model.api.v1.trade.TradeOutputDto;
import com.six.challenge.tradingplatform.model.database.OrderType;

import java.util.List;
import java.util.UUID;

public class OrderOutputDto {

    private UUID id;
    private UUID userId;
    private UUID securityId;
    private boolean fulfilled;
    private Double price;
    private Long quantity;
    private Long currentQuantity;
    private OrderType type;
    private List<TradeOutputDto> trades;

    public OrderOutputDto(UUID id, UUID userId, UUID securityId, boolean fulfilled,
                          Double price, Long quantity, Long currentQuantity, OrderType type) {
        this.id = id;
        this.userId = userId;
        this.securityId = securityId;
        this.fulfilled = fulfilled;
        this.price = price;
        this.quantity = quantity;
        this.currentQuantity = currentQuantity;
        this.type = type;
    }

    public OrderOutputDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getSecurityId() {
        return securityId;
    }

    public void setSecurityId(UUID securityId) {
        this.securityId = securityId;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Long currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public List<TradeOutputDto> getTrades() {
        return trades;
    }

    public void setTrades(List<TradeOutputDto> trades) {
        this.trades = trades;
    }

    @Override
    public String toString() {
        return "OrderOutputDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", securityId=" + securityId +
                ", fulfilled=" + fulfilled +
                ", price=" + price +
                ", quantity=" + quantity +
                ", currentQuantity=" + currentQuantity +
                ", type=" + type +
                ", trades=" + trades +
                '}';
    }
}
