package com.six.challenge.tradingplatform.constants;

public class Queries {


    // User
    public static final String FIND_USER_BY_NAME
            = "SELECT * FROM " + Tables.USER + " u WHERE u.name = :name";

    // Security
    public static final String FIND_SECURITY_BY_NAME
            = "SELECT * FROM " + Tables.SECURITY + " u WHERE u.name = :name";
}
