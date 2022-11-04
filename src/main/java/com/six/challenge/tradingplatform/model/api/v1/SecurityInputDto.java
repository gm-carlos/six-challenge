package com.six.challenge.tradingplatform.model.api.v1;

import com.six.challenge.tradingplatform.model.database.SecurityDao;

import java.util.UUID;

public class SecurityInputDto {

    private String name;

    public SecurityInputDto(UUID id, String name) {
        this.name = name;
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
