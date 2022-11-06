package com.six.challenge.tradingplatform.model.api.v1.security;

import java.util.UUID;

public class SecurityOutputDto {

    private UUID id;
    private String name;

    SecurityOutputDto() {
    }

    public SecurityOutputDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
