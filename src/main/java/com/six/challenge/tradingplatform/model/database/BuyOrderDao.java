package com.six.challenge.tradingplatform.model.database;

import com.six.challenge.tradingplatform.model.api.v1.OrderDto;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "BUY_ORDER")
public class BuyOrderDao extends OrderDao {

    public BuyOrderDao() { super(); }

    public BuyOrderDao(UUID userId, UUID securityId, UUID fulfilledId, Long quantity) {
        super(userId, securityId, fulfilledId, quantity);
        this.setType(OrderType.BUY);
    }
}
