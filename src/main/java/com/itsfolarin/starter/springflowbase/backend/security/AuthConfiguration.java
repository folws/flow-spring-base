package com.itsfolarin.starter.springflowbase.backend.security;

import com.itsfolarin.starter.springflowbase.backend.repo.RememberMeTokenRepository;
import com.itsfolarin.starter.springflowbase.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class AuthConfiguration extends GlobalMethodSecurityConfiguration {

    static {
        SecurityContextHolder.setStrategyName(VaadinSessionSecurityContextHolderStrategy.class.getName());
    }
    https://home.zoho.com/home
    private final AuthProperties properties;
    private final UserDetailsService userService;
    private final RememberMeTokenRepository tokenRepository;


    @Autowired
    public AuthConfiguration(AuthProperties properties, UserDetailsService userService, RememberMeTokenRepository tokenRepository) {
        this.properties = properties;
        this.userService = userService;
        this.tokenRepository = tokenRepository;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(userDetailsService())
                .and()
                .authenticationProvider(daoAuthenticationProvider());

    }

    private UserDetailsService userDetailsService() {
        return userService;
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setPasswordEncoder(passwordEncoder());
        dao.setUserDetailsService(userDetailsService());

        return dao;
    }

    @Bean
    public AuthenticationProvider cookieTokenAuthenticationProvider() {
        return new CookieTokenAuthenticationProvider(properties, (UserService) userDetailsService(), tokenRepository);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }
}
