package com.six.challenge.tradingplatform.model;

import com.six.challenge.tradingplatform.model.database.OrderDao;
import com.six.challenge.tradingplatform.model.database.TradeDao;

import java.util.ArrayList;
import java.util.List;

public class TradeResult {

    private List<OrderDao> orders = new ArrayList<>();
    private List<TradeDao> trades = new ArrayList<>();
    public TradeResult() {};

    public void addOrder(OrderDao order) {
        orders.add(order);
    }

    public void addTrade(TradeDao trade) {
        trades.add(trade);
    }

    public List<OrderDao> getOrders() {
        return orders;
    }

    public List<TradeDao> getTrades() {
        return trades;
    }
}
