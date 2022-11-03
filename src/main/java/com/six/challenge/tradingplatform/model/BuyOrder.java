package com.six.challenge.tradingplatform.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "BUY_ORDER")
public class BuyOrder extends Order{

    public BuyOrder() { super(); }

    public BuyOrder(UUID userId, UUID securityId, UUID fulfilledId, Long quantity) {
        super(userId, securityId, fulfilledId, quantity);
        type = OrderType.BUY;
    }
}
