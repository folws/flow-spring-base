package com.itsfolarin.starter.springflowbase.backend.service;

import com.itsfolarin.starter.springflowbase.backend.domain.User;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
@Service
@SessionScope
public class AuthService {

    public static final String CURRENT_USER = "CURRENT_USER";
    public static final String PREV_SESSION = "PREVIOUS_SESSION";
    private static final String COOKIE_NAME = "teach-hub-cookie";

    private static AuthService INSTANCE;

    private final PasswordEncoder passwordEncoder;
    private final VaadinSession session;
    private final AuthenticationManager authManager;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder, AuthenticationManager authManager) {
        if (INSTANCE != null) throw new IllegalStateException("Singleton class, instance already exist.");

        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.session = VaadinSession.getCurrent();

        INSTANCE = this;
    }

    public static boolean isLoggedIn() {
        return getCurrentUser() != null;
    }

    public static boolean isLoggedIn(VaadinSession session) {
        return getCurrentUser(session) != null;
    }

    public static String encodePlainTextPassword(String password) {
        return get().passwordEncoder.encode(password);
    }

    public static User getCurrentUser() {
        return getCurrentUser(SecurityContextHolder.getContext());
    }

    public static User getCurrentUser(VaadinSession session) {
        return session != null ? getCurrentUser(session.getAttribute(SecurityContext.class)) : null;
    }

    private static User getCurrentUser(SecurityContext context) {
        Authentication authentication = context != null ? context.getAuthentication() : null;

        return authentication != null ? (User) authentication.getDetails() : null;
    }

    public static AuthService get() {
        return INSTANCE;
    }


    /**
     * @param securedClass the class annotated with @{@link Secured}
     * @return whether logged in user has authority to access {@code securedClass}
     */
    public static boolean isAccessGranted(@NonNull Class<?> securedClass) {
        Secured secured = AnnotationUtils.getAnnotation(securedClass, Secured.class);

        if (secured == null)
            return true;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.isAuthenticated())
            return false;

        List<String> allowedRoles = Arrays.asList(secured.value());


        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).
                anyMatch(allowedRoles::contains);
    }

    /**
     * @param username  username of user
     * @param password  password of the user.
     * @param autoLogin if the service should attempt to login using cache.
     * @return user obj if credentials match or null otherwise.
     */
    public User login(String username, String password, boolean autoLogin) throws AuthenticationException {
        if (username == null || password == null)
            return null;

        username = username.toLowerCase();

        return loginWithCredentials(new UsernamePasswordAuthenticationToken(username, password), autoLogin);
    }

    private User loginWithCredentials(AbstractAuthenticationToken token, boolean autoLogin) throws AuthenticationException {
        User userFound;

        if (isLoggedIn(session))
            throw new IllegalStateException("Already have a user that is logged in. First use Auth#logOut()");

        if (!autoLogin || (userFound = autoLogin()) == null) {
            Authentication authentication = authManager.authenticate(token);

            if (authentication.getDetails() instanceof User && authentication.isAuthenticated())
                return (User) authentication.getDetails();
            else
                throw new BadCredentialsException("Any error occurred when trying to login.");
        }

        return userFound;
    }

    /**
     * Check if a stored token in cookie exists and uses token to authenticate user.
     *
     * @return user that is authenticated
     */
    private User autoLogin() {
        try {
            Authentication authentication = authManager.authenticate(new RememberMeAuthenticationToken("",
                    "", null));

            if (authentication.isAuthenticated())
                return (User) authentication.getPrincipal();
        } catch (AuthenticationException ignored) {

        }

        return null;
    }

    public void logOut() {
        if (!isLoggedIn(session))
            throw new IllegalStateException("No user is logged in.");

        User user = getCurrentUser(session);
//
//        user.getCredentials().setEncodedCookieToken(PersistableRememberMeToken.gen());
//        Auth.invalidateLastCookie(VaadinResponse.getCurrent());
    }

}
