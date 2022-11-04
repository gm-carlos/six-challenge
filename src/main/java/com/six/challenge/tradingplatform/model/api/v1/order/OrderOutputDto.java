package com.six.challenge.tradingplatform.model.api.v1.order;

import com.six.challenge.tradingplatform.model.database.OrderType;

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
}