package com.six.challenge.tradingplatform.model.database;

import com.six.challenge.tradingplatform.model.api.v1.order.OrderOutputDto;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.Date;
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
    @ManyToOne
    @JoinColumn(name="userId", nullable=false)
    private UserDao user;
    @ManyToOne
    @JoinColumn(name="securityId", nullable=false)
    private SecurityDao security;
    private boolean fulfilled;
    @Positive(message = "The value must be positive")
    private Double price;
    @Positive(message = "The value must be positive")
    private Long quantity;
    private Long currentQuantity;
    private OrderType type;
    private Date createdAt;


    public OrderDao() {}

    public OrderDao(UserDao user, SecurityDao security, Double price, Long quantity, OrderType type) {
        this.user = user;
        this.security = security;
        this.fulfilled = false;
        this.price = price;
        this.quantity = quantity;
        this.currentQuantity = quantity;
        this.type = type;
        this.createdAt = new Date();
    }

    public UUID getId() {
        return id;
    }

    public UserDao getUser() {
        return user;
    }

    public void setUserId(UserDao userId) {
        this.user = user;
    }

    public SecurityDao getSecurity() {
        return security;
    }

    public void setSecurityId(SecurityDao security) {
        this.security = security;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public OrderOutputDto toDto() {
        return new OrderOutputDto(
                this.getId(),
                this.getUser().getId(),
                this.getSecurity().getId(),
                this.isFulfilled(),
                this.getPrice(),
                this.getQuantity(),
                this.getCurrentQuantity(),
                this.getType());
    }
}
