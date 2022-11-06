package com.six.challenge.tradingplatform.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlgorithmNotValidException extends ResponseStatusException {

    public AlgorithmNotValidException(String algorithm) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Error instantiating class for algorithm not found: " + algorithm);
    }
}
