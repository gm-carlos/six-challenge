package com.six.challenge.tradingplatform.model.api.v1;

import com.six.challenge.tradingplatform.model.database.UserDao;

import java.util.UUID;

public class UserInputDto {

    private String name;
    private String password;

    public UserInputDto(String name, String password) {
        this.name = name;
        this.password = password;
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
