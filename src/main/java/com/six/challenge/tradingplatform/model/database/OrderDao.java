package com.six.challenge.tradingplatform.model.database;

import com.six.challenge.tradingplatform.model.api.v1.order.OrderOutputDto;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public abstract class OrderDao {

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "UUID", strategy = "uuid4")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;
    private UUID userId;
    private UUID securityId;
    private boolean fulfilled;
    @Positive(message = "The value must be positive")
    private Double price;
    @Positive(message = "The value must be positive")
    private Long quantity;
    private Long currentQuantity;
    private OrderType type;

    public OrderDao() {}

    public OrderDao(UUID userId, UUID securityId, Double price, Long quantity, OrderType type) {
        this.userId = userId;
        this.securityId = securityId;
        this.fulfilled = false;
        this.price = price;
        this.quantity = quantity;
        this.currentQuantity = quantity;
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

    public boolean isFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(boolean fulfilledId) {
        this.fulfilled = fulfilledId;
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

    protected OrderType getType() {
        return type;
    }

    protected void setType(OrderType type) {
        this.type = type;
    }

    public Long getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Long currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public OrderOutputDto toDto() {
        return new OrderOutputDto(
                this.getId(),
                this.getUserId(),
                this.getSecurityId(),
                this.isFulfilled(),
                this.getPrice(),
                this.getQuantity(),
                this.getCurrentQuantity(),
                this.getType());
    }
}
