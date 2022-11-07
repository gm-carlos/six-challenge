package com.six.challenge.tradingplatform.business;

import com.six.challenge.tradingplatform.model.TradeResult;
import com.six.challenge.tradingplatform.model.database.OrderDao;

import java.util.List;

public interface OrderMatchingAlgorithm {
    TradeResult executeOrder(OrderDao currentOrder, List<OrderDao> matchingOrders);
}
