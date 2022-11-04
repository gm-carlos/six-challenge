package com.six.challenge.tradingplatform.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UUID id) {
        super("Could not find user with id " + id);
    }
    public UserNotFoundException(String name) {
        super("Could not find user with name " + name);
    }
}
