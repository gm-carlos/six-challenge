package com.six.challenge.tradingplatform.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public class SecurityNotFoundException extends ResponseStatusException {

    public SecurityNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND, "Could not find security with id " + id);
    }

    public SecurityNotFoundException(String name) {
        super(HttpStatus.NOT_FOUND, "Could not find security with name " + name);
    }

}
