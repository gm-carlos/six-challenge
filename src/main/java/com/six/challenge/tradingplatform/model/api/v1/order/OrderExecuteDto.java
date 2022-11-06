package com.six.challenge.tradingplatform.model.api.v1.order;

public class OrderExecuteDto {

    private String algorithm = "";

    public OrderExecuteDto() {
    }

    public OrderExecuteDto(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }
}
