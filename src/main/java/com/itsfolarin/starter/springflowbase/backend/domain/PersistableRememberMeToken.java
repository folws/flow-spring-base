package com.itsfolarin.starter.springflowbase.backend.domain;

import com.itsfolarin.starter.springflowbase.backend.security.RememberMeToken;
import com.itsfolarin.starter.springflowbase.backend.AbstractDocument;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Document
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
public class PersistableRememberMeToken extends AbstractDocument implements RememberMeToken {

    @Indexed(unique = true)
    private final String series;
    private String token;
    private String userName;
    private long tokenValidityTime;

    public PersistableRememberMeToken(String userName, String token) {
        this(userName, makeSeriesIdentifier(userName), token);
    }

    private PersistableRememberMeToken(String userName, String series, String token) {
        this.userName = userName;
        this.series = series;
        this.token = token;
        this.tokenValidityTime = expireDate().getTime();
    }

    public static String makeTokenSignature(String series, String token) {
        return encodeUsingMD5(series + "/" + token);
    }

    public static String makeSeriesIdentifier(String userName) {
        return encodeUsingMD5(userName) + "//" + UUID.randomUUID().toString();
    }

    protected static String encodeUsingMD5(String data) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var8) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }

        return new String(Hex.encode(digest.digest(data.getBytes())));
    }

    private static Date expireDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, COOKIE_EXPIRE_DAY);

        return cal.getTime();
    }

    public static PersistableRememberMeToken assignNewToken(String userName) {
        return new PersistableRememberMeToken(userName, UUID.randomUUID().toString());
    }

    public static PersistableRememberMeToken assignNewToken(String userName, String series) {
        return new PersistableRememberMeToken(userName, series, UUID.randomUUID().toString());
    }

    @Override
    public boolean isTokenEncoded() {
        return !isNew();
    }

    @Override
    public String getSeries() {
        return series;
    }

    @Override
    public void onPersist() {
        this.token = makeTokenSignature(getSeries(), getToken());
    }

    public boolean isTokenExpired() {
        return getTokenValidityTime() > System.currentTimeMillis();
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean doesTokenMatch(String presentedToken, String presentedSeries) {
        return isTokenEncoded() ? assignNewToken(null, presentedSeries).getToken().equals(getToken())
                : presentedToken.equals(token);
    }
}
