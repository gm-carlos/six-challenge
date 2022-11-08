package com.six.challenge.tradingplatform.model;

import com.six.challenge.tradingplatform.model.database.OrderDao;
import com.six.challenge.tradingplatform.model.database.TradeDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TradeResult {

    private Set<OrderDao> orders = new HashSet<>();
    private Set<TradeDao> trades = new HashSet<>();
    public TradeResult() {};

    public void addOrder(OrderDao order) {
        orders.add(order);
    }

    public void addTrade(TradeDao trade) {
        trades.add(trade);
    }

    public Set<OrderDao> getOrders() {
        return orders;
    }

    public Set<TradeDao> getTrades() {
        return trades;
    }

    @Override
    public String toString() {
        return "TradeResult{" +
                "orders=" + orders +
                ", trades=" + trades +
                '}';
    }
}
