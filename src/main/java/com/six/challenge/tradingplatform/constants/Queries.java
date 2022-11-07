package com.six.challenge.tradingplatform.constants;

public class Queries {


    // User
    public static final String FIND_USER_BY_NAME
            = "SELECT * FROM " + Tables.USER + " u WHERE u.name = :name";

    // Security
    public static final String FIND_SECURITY_BY_NAME
            = "SELECT * FROM " + Tables.SECURITY + " s WHERE s.name = :name";

    // Orders
    public static final String FIND_MATCHING_SELL_ORDERS
            = "SELECT * FROM " + Tables.ORDER +
            " o WHERE o.user_id != :userId AND o.type = 'SELL' AND o.price <= :price AND o.fulfilled = FALSE" +
            " AND o.security_id = :securityId";

    public static final String FIND_MATCHING_BUY_ORDERS
            = "SELECT * FROM " + Tables.ORDER +
            " o WHERE o.user_id != :userId AND o.type = 'BUY' AND o.price >= :price AND o.fulfilled = FALSE" +
            " AND o.security_id = :securityId";
}
