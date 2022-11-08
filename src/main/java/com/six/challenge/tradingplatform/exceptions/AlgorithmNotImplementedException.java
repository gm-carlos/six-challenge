package com.six.challenge.tradingplatform.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlgorithmNotImplementedException extends ResponseStatusException {

    public AlgorithmNotImplementedException(String algorithm) {
        super(HttpStatus.BAD_REQUEST, "Algorithm: " + algorithm + " is not implemented");
    }
}
