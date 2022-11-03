package com.six.challenge.tradingplatform.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public abstract class Order {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private UUID id;

    private UUID userId;
    private UUID securityId;
    private UUID fulfilledId;
    private Long quantity;
    @Transient
    protected OrderType type;

    public Order() {}

    public Order(UUID userId, UUID securityId, UUID fulfilledId, Long quantity) {
        this.userId = userId;
        this.securityId = securityId;
        this.fulfilledId = fulfilledId;
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "SellOrder{" +
                "id=" + id +
                ", userId=" + userId +
                ", securityId=" + securityId +
                ", fulfilledId=" + fulfilledId +
                ", quantity=" + quantity +
                ", type=" + type +
                '}';
    }
}
