package com.six.challenge.tradingplatform.exceptions;

import com.six.challenge.tradingplatform.model.database.OrderType;

public class InvalidOrderException extends RuntimeException {

    public InvalidOrderException(OrderType type) {
        super("Invalid order type " + type);
    }

    public InvalidOrderException(String errors) {
        super("Error in data validation: " + errors);
    }


}
