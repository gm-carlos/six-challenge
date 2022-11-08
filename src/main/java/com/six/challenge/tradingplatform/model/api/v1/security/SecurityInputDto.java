package com.six.challenge.tradingplatform.model.api.v1.security;

import com.six.challenge.tradingplatform.model.database.SecurityDao;

public class SecurityInputDto {

    private String name;

    SecurityInputDto() {
    }

    public SecurityInputDto(String name) {
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

    @Override
    public String toString() {
        return "SecurityInputDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
