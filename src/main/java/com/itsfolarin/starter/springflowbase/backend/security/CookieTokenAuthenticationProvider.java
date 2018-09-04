package com.itsfolarin.starter.springflowbase.backend.security;

import com.itsfolarin.starter.springflowbase.backend.domain.PersistableRememberMeToken;
import com.itsfolarin.starter.springflowbase.backend.domain.User;
import com.itsfolarin.starter.springflowbase.backend.repo.RememberMeTokenRepository;
import com.itsfolarin.starter.springflowbase.backend.service.UserService;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.stream.Stream;

@Log4j2
public class CookieTokenAuthenticationProvider implements AuthenticationProvider, MessageSourceAware {

    private static String cookieName;
    private final UserService userService;
    private final RememberMeTokenRepository tokenRepository;
    private MessageSourceAccessor messages;

    public CookieTokenAuthenticationProvider(AuthProperties properties, UserService userService,
                                             RememberMeTokenRepository tokenRepository) {
        cookieName = properties.getRememberMeCookieName();
        this.userService = userService;
        this.tokenRepository = tokenRepository;
    }

    protected static void deleteCookie(VaadinResponse response) {
        setCookie(response, "", 0);
    }

    protected static void addCookie(RememberMeToken token, VaadinResponse response) {
        String tokenValue = token.getSeries() + ":" + token.getToken();

        setCookie(response, tokenValue, RememberMeToken.COOKIE_EXPIRE_DAY * 3600 * 24);
    }

    protected static void setCookie(VaadinResponse response, String value, int age) {
        Cookie cookie = new Cookie(cookieName, value);

        cookie.setSecure(true);
        cookie.setMaxAge(age);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        User user;

        if (!this.supports(auth.getClass())) return null;
        else if ((user = processAutoLogin(fetchTokensFromBrowser(),
                (VaadinResponse) auth.getPrincipal())) != null)
            return new RememberMeAuthenticationToken("", user, user.getAuthorities());
        else {
            auth.setAuthenticated(false);
            return auth;
        }
    }

    protected User processAutoLogin(String[] cookieTokens, VaadinResponse response) throws AuthenticationException {
        if (cookieTokens.length != 2) {
            throw new InvalidCookieException("Cookie token did not contain 2 tokens, but contained '" + Arrays.asList(cookieTokens) + "'");
        } else {
            String presentedSeries = cookieTokens[0];
            String presentedToken = cookieTokens[1];
            RememberMeToken token = this.tokenRepository.findBySeries(presentedSeries);

            if (token == null)
                throw new RememberMeAuthenticationException("No persistent token found for series id: " + presentedSeries);

            if (!token.doesTokenMatch(presentedToken, presentedSeries)) {
                this.tokenRepository.removeAllByUserName(token.getUsername());
                deleteCookie(response);
                throw new CookieTheftException(this.messages.getMessage("PersistentTokenBasedRememberMeServices.cookieStolen", "Invalid remember-me token (Series/token) mismatch. Implies previous cookie theft attack."));
            } else if (token.isTokenExpired())
                throw new RememberMeAuthenticationException("Remember-me login has expired");

            else {
                if (log.isDebugEnabled())
                    log.debug("Refreshing persistent login token for user '" + token.getUsername() + "', series '" + token.getSeries() + "'");

                PersistableRememberMeToken newToken = PersistableRememberMeToken.assignNewToken(token.getUsername(), token.getSeries());

                try {
                    this.tokenRepository.delete((PersistableRememberMeToken) token);
                    this.tokenRepository.save(newToken);
                    addCookie(newToken, response);

                } catch (Exception var9) {
                    log.error("Failed to update token: ", var9);
                    throw new RememberMeAuthenticationException("Autologin failed due to data access problem");
                }
                return (User) this.userService.loadUserByUsername(token.getUsername());
            }
        }
    }

    private String[] fetchTokensFromBrowser() {
        Cookie[] cookies = VaadinRequest.getCurrent().getCookies();

        return Stream.of(cookies).filter(cookie -> cookie.getName().equals(cookieName)).findFirst().map(cookie ->
                cookie.getValue().split(":")).orElse(new String[0]);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return RememberMeAuthenticationToken.class.isAssignableFrom(aClass);
    }

    @Override
    public void setMessageSource(@NonNull MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}
