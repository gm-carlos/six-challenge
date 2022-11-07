package com.six.challenge.tradingplatform.at.client.v1.model;

import org.springframework.http.HttpStatus;

public class BaseHTTPResponse {

    private HttpStatus status;
    private String rawResponse;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(String response) {
        this.rawResponse = response;
    }
}
