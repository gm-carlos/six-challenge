package com.six.challenge.tradingplatform.model.api.v1.security;

import com.six.challenge.tradingplatform.model.database.SecurityDao;

import java.util.UUID;

public class SecurityUpdateInputDto {

    private UUID id;
    private String name;

    public SecurityUpdateInputDto(UUID id, String name) {
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

    public SecurityDao toDao() {
        return new SecurityDao(this.getName());
    }
}
