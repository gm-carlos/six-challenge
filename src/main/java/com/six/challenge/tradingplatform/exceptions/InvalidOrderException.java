package com.six.challenge.tradingplatform.exceptions;

import com.six.challenge.tradingplatform.model.database.OrderType;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class InvalidOrderException extends ResponseStatusException {

    public InvalidOrderException(OrderType type) {
        super(HttpStatus.BAD_REQUEST, "Invalid order type " + type);
    }

    public InvalidOrderException(String errors) {
        super(HttpStatus.BAD_REQUEST, "Data validation error (" + errors + ")");
    }


}
