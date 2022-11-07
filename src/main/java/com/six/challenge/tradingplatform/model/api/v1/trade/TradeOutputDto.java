package com.six.challenge.tradingplatform.model.api.v1.trade;

import java.util.UUID;

public class TradeOutputDto {

    private UUID id;
    private UUID sellOrderId;
    private UUID buyOrderId;
    private Double price;
    private Long quantity;

    public TradeOutputDto(UUID id, UUID sellOrderId, UUID buyOrderId,Double price, Long quantity) {
        this.id = id;
        this.sellOrderId = sellOrderId;
        this.buyOrderId = buyOrderId;
        this.price = price;
        this.quantity = quantity;

    }

    public TradeOutputDto() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public UUID getSellOrderId() {
        return sellOrderId;
    }

    public void setSellOrderId(UUID sellOrderId) {
        this.sellOrderId = sellOrderId;
    }

    public UUID getBuyOrderId() {
        return buyOrderId;
    }

    public void setBuyOrderId(UUID buyOrderId) {
        this.buyOrderId = buyOrderId;
    }

    @Override
    public String toString() {
        return "TradeOutputDto{" +
                "id=" + id +
                ", sellOrderId=" + sellOrderId +
                ", buyOrderId=" + buyOrderId +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
