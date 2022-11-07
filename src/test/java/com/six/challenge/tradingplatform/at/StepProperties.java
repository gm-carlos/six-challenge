package com.six.challenge.tradingplatform.at;

import com.six.challenge.tradingplatform.at.client.v1.TradingClient;

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
}
