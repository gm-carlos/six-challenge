package com.six.challenge.tradingplatform.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlgorithmNotFoundException extends ResponseStatusException {

    public AlgorithmNotFoundException(String algorithm) {
        super(HttpStatus.BAD_REQUEST, "Class for algorithm not found: " + algorithm);
    }
}
