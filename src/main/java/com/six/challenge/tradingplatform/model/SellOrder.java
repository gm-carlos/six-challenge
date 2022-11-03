package com.six.challenge.tradingplatform.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "SELL_ORDER")
public class SellOrder extends Order {

    public SellOrder() { super(); }

    public SellOrder(UUID userId, UUID securityId, UUID fulfilledId, Long quantity) {
        super(userId, securityId, fulfilledId, quantity);
        type = OrderType.BUY;
    }
}
