package com.six.challenge.tradingplatform.exceptions;

import java.util.UUID;

public class SecurityNotFoundException extends RuntimeException {

    public SecurityNotFoundException(UUID id) {
        super("Could not find security with id " + id);
    }
    public SecurityNotFoundException(String name) {
        super("Could not find security with name " + name);
    }

}
