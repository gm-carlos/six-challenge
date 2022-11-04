package com.six.challenge.tradingplatform.model.api.v1.order;

import com.six.challenge.tradingplatform.model.database.BuyOrderDao;
import com.six.challenge.tradingplatform.model.database.OrderType;
import com.six.challenge.tradingplatform.model.database.SellOrderDao;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public class OrderInputDto {

    private UUID userId;
    private UUID securityId;
    @Schema(example = "1")
    private Double price;
    @Schema(example = "1")
    private Long quantity;
    private OrderType type;

    public OrderInputDto(UUID userId, UUID securityId, Double price, Long quantity, OrderType type) {
        this.userId = userId;
        this.securityId = securityId;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
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

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public BuyOrderDao toBuyOrderDao() {
        return new BuyOrderDao(
                this.getUserId(), this.getSecurityId(), this.getPrice(), this.getQuantity());
    }

    public SellOrderDao toSellOrderDao() {
        return new SellOrderDao(
                this.getUserId(), this.getSecurityId(), this.getPrice(), this.getQuantity());
    }
}
