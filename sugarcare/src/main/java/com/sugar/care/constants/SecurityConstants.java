package com.sugar.care.constants;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 14400000; // 4hrs validity now ,5 days expressed in milliseconds 432_000_000
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String SUGAR_CARE = "Sugar Care";

    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = { "/user/login", "/swagger-ui.html", "/user/register" };
}
