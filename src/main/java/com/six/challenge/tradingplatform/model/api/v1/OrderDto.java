package com.six.challenge.tradingplatform.model.api.v1;

import com.six.challenge.tradingplatform.model.database.OrderType;

import java.util.UUID;

public class OrderDto {

    private UUID id;
    private UUID userId;
    private UUID securityId;
    private UUID fulfilledId;
    private Long quantity;
    private OrderType type;

    public OrderDto(UUID id, UUID userId, UUID securityId, UUID fulfilledId, Long quantity, OrderType type) {
        this.id = id;
        this.userId = userId;
        this.securityId = securityId;
        this.fulfilledId = fulfilledId;
        this.quantity = quantity;
        this.type = type;
    }

    public UUID getId() {
        return id;
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

    public UUID getFulfilledId() {
        return fulfilledId;
    }

    public void setFulfilledId(UUID fulfilledId) {
        this.fulfilledId = fulfilledId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
