package com.six.challenge.tradingplatform.cucumber.client.v1.model;

import java.util.List;

public class HTTPResponseList<T> extends BaseHTTPResponse {

    private List<T> responseObjectList;

    public List<T> getResponseObjectList() {
        return responseObjectList;
    }

    public void setResponseObjectList(List<T> responseObjectList) {
        this.responseObjectList = responseObjectList;
    }
}
