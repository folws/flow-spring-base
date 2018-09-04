package com.itsfolarin.starter.springflowbase.backend.domain;

import com.helger.commons.annotation.PrivateAPI;
import com.itsfolarin.starter.springflowbase.backend.service.AuthService;
import com.itsfolarin.starter.springflowbase.Control;
import com.itsfolarin.starter.springflowbase.backend.AbstractDocument;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Document
@Getter
public class User extends AbstractDocument implements UserDetails {

    @Indexed(unique = true, dropDups = true)
    @NotNull(message = "Name is required.")
    @Size(min = Control.MIN_NAME_LENGTH, max = Control.MAX_NAME_LENGTH,
            message = "username must have at least " + Control.MIN_NAME_LENGTH + " letters.")
    private String userName;

    @Indexed
    @NotNull(message = "Name is required.")
    @Size(min = Control.MIN_NAME_LENGTH, max = Control.MAX_NAME_LENGTH,
            message = "name must have at least " + Control.MIN_NAME_LENGTH + "  letters.")
    private String firstName, lastName;

    @Indexed
    private String email;

    private boolean locked;
    private boolean enabled; // true when email is verified

    private SimpleCredentials credentials;

    @PersistenceConstructor
    protected User(BigInteger id, String userName, String firstName, String lastName, String email, SimpleCredentials credentials) {
        super(id);

        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.credentials = credentials;
    }

    private User(String userName, String plainTextPass) {
        this.userName = userName;
        this.credentials = new SimpleCredentials(AuthService.encodePlainTextPassword(plainTextPass), null);
    }

    @PrivateAPI
    public static User from(UserDetails details) {
        User user = new User(details.getUsername(), details.getPassword());

        user.locked = !details.isAccountNonLocked();
        user.enabled = details.isEnabled();

        if (!details.isCredentialsNonExpired())
            user.credentials.setExpiryDate(new Date(System.currentTimeMillis()));

        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return credentials.getPassword();
    }

    @Override
    public String getUsername() {
        return credentials.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentials.isExpired();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Setter
    @Getter
    public class SimpleCredentials {

        @Transient
        private PersistableRememberMeToken persistableRememberMeToken;
        @Setter(AccessLevel.NONE)
        private String cookieTokenKey;
        @Setter(AccessLevel.NONE)
        private String encodedPassword;
        private Date expiryDate;

        @PersistenceConstructor
        protected SimpleCredentials(String password, Date expiryDate) {
            this.encodedPassword = password;
            this.expiryDate = expiryDate;

//            if (token != null && !token.isTokenExpired())
//                this.persistableRememberMeToken = token;
        }

        public boolean isExpired() {
            return expiryDate != null && expiryDate.before(new Date(System.currentTimeMillis()));
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return encodedPassword;
        }

    }
}
