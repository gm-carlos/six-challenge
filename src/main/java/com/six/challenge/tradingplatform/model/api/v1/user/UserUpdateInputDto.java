package com.six.challenge.tradingplatform.model.api.v1.user;

import com.six.challenge.tradingplatform.model.database.UserDao;

import java.util.UUID;

public class UserUpdateInputDto {

    private UUID id;
    private String name;
    private String password;

    public UserUpdateInputDto(UUID id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDao toDao() {
        return new UserDao(this.getName(), this.getPassword());
    }
}
