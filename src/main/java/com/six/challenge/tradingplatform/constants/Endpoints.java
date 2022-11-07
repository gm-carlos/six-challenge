package com.six.challenge.tradingplatform.constants;

public class Endpoints {

    // Version
    private static final String V1 = "/v1";

    // Common
    private static final String BY_ID = "/{id}";
    private static final String BY_NAME = "/{name}";
    public static final String FIND_ALL = "/findAll";
    public static final String FIND_BY_ID = "/findById";
    public static final String FIND_BY_NAME= "/findByName";
    public static final String FIND_BY_ID_WITH_PARAM = FIND_BY_ID + BY_ID;
    public static final String FIND_BY_NAME_WITH_PARAM = FIND_BY_NAME + BY_NAME;
    public static final String CREATE = "/create";
    public static final String UPDATE = "/update";
    private static final String DELETE = "/delete";
    public static final String DELETE_BY_ID = DELETE + BY_ID;

    // User
    private static final String USER = "/user";
    public static final String USER_V1 =  V1 + USER;

    // Security
    private static final String SECURITY = "/security";
    public static final String SECURITY_V1 =  V1 + SECURITY;

    // ORDER
    private static final String ORDER = "/order";
    public static final String ORDER_V1 =  V1 + ORDER;
}
