package com.six.challenge.tradingplatform.business;

import com.six.challenge.tradingplatform.model.database.BuyOrderDao;
import com.six.challenge.tradingplatform.model.database.SellOrderDao;

import java.util.List;

public interface OrderMatchingAlgorithm {
    void executeOrders(List<BuyOrderDao> buyOrders, List<SellOrderDao> sellOrders);
}
