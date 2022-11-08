package com.six.challenge.tradingplatform.model.api.v1.user;

import com.six.challenge.tradingplatform.model.database.UserDao;

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

    @Override
    public String toString() {
        return "UserInputDto{" +
                "name='" + name + '\'' +
                ", password='****" + '\'' +
                '}';
    }
}
