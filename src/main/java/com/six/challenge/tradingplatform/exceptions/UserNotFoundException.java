package com.six.challenge.tradingplatform.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public class UserNotFoundException extends ResponseStatusException {

    public UserNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND, "Could not find user with id " + id);
    }
    public UserNotFoundException(String name) {
        super(HttpStatus.NOT_FOUND, "Could not find user with name " + name);
    }
}
