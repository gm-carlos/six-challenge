package com.six.challenge.tradingplatform.at;

import com.six.challenge.tradingplatform.at.client.v1.TradingClient;

import java.util.ArrayList;
import java.util.List;

public class StepProperties {

    private static TradingClient CLIENT;
    public static String HOST = "localhost";
    public static String PORT = "8080";

    public static TradingClient getClientInstance() {
        if (CLIENT == null || CLIENT.getClient() == null || CLIENT.getClient().isClosed()) {
            CLIENT = new TradingClient(HOST, PORT);
        }
        return CLIENT;
    }

    private List<String> orderIds = new ArrayList<>();

    public void cleanOrderIds() {
        orderIds.clear();
    }

    public void addOrderId(String id) {
        orderIds.add(id);
    }

    public String getOrderId(int index) {
        return orderIds.get(index);
    }
}
