package com.six.challenge.tradingplatform.model.database;

import com.six.challenge.tradingplatform.constants.Tables;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = Tables.TRADE)
public class TradeDao {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type="uuid-char")
    private UUID id;
    @ManyToOne
    @JoinColumn(name="sellOrderId", nullable=false)
    private OrderDao sellOrder;
    @ManyToOne
    @JoinColumn(name="buyOrderId", nullable=false)
    private OrderDao buyOrder;
    private Double price;
    private Long quantity;
    private Date createdAt;

    public TradeDao(OrderDao sellOrder, OrderDao buyOrder, Double price, Long quantity) {
        this.sellOrder = sellOrder;
        this.buyOrder = buyOrder;
        this.price = price;
        this.quantity = quantity;
        this.createdAt = new Date();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OrderDao getSellOrderId() {
        return sellOrder;
    }

    public void setSellOrderId(OrderDao sellOrder) {
        this.sellOrder = sellOrder;
    }

    public OrderDao getBuyOrderId() {
        return buyOrder;
    }

    public void setBuyOrderId(OrderDao buyOrder) {
        this.buyOrder = buyOrder;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "TradeDao{" +
                "id=" + id +
                ", sellOrderId=" + sellOrder.getId() +
                ", buyOrderId=" + buyOrder.getId() +
                ", price=" + price +
                ", quantity=" + quantity +
                ", createdAt=" + createdAt +
                '}';
    }
}
