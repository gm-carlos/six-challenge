package com.six.challenge.tradingplatform.model.database;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "SELL_ORDER")
public class SellOrderDao extends OrderDao {

    public SellOrderDao() { super(); }

    public SellOrderDao(UUID userId, UUID securityId, UUID fulfilledId, Long quantity) {
        super(userId, securityId, fulfilledId, quantity);
        this.setType(OrderType.SELL);
    }
}
