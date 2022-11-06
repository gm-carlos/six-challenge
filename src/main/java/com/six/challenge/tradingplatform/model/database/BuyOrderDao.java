package com.six.challenge.tradingplatform.model.database;

import com.six.challenge.tradingplatform.constants.Tables;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = Tables.BUYORDER)
public class BuyOrderDao extends OrderDao {

    public BuyOrderDao() { super(); }

    public BuyOrderDao(UserDao user, SecurityDao security, Double price, Long quantity) {
        super(user, security, price, quantity, OrderType.BUY);
    }
}
