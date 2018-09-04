package com.itsfolarin.starter.springflowbase.backend.security;

public interface RememberMeToken {
    int COOKIE_EXPIRE_DAY = 30;

    String getToken();

    String getSeries();

    long getTokenValidityTime();

    boolean isTokenEncoded();

    boolean isTokenExpired();

    String getUsername();

    boolean doesTokenMatch(String presentedToken, String presentedSeries);
}
