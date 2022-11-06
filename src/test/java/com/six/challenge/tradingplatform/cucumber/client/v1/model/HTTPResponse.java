package com.six.challenge.tradingplatform.cucumber.client.v1.model;

public class HTTPResponse<T> extends BaseHTTPResponse {

    private T responseObject;

    public T getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(T responseObject) {
        this.responseObject = responseObject;
    }
}
