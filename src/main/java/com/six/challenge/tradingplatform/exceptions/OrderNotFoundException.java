package com.six.challenge.tradingplatform.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public class OrderNotFoundException extends ResponseStatusException {

    public OrderNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND, "Could not find order with id " + id);
    }
}
