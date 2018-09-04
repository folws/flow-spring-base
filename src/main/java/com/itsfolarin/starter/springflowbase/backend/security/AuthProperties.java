package com.itsfolarin.starter.springflowbase.backend.security;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth")
@Getter
@Setter
public class AuthProperties {

    private Integer serverInteger;
    private String serverSecret;
    private String rememberMeCookieName;

    private int tokenSeriesLength = 16;
    private int tokenLength = 17;
}
