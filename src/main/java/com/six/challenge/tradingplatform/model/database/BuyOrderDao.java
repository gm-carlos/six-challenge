package com.six.challenge.tradingplatform.model.database;

import com.six.challenge.tradingplatform.constants.Tables;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = Tables.BUYORDER)
public class BuyOrderDao extends OrderDao {

    public BuyOrderDao() { super(); }

    public BuyOrderDao(UUID userId, UUID securityId, Double price, Long quantity) {
        super(userId, securityId, price, quantity, OrderType.BUY);
    }
}
