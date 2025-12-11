package com.gt.planit.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JWTUtil {

    private final SecretKey key;

    private final long accessExpMin;
    private final long refreshExpMin;

    public JWTUtil(
            @Value("${jwt.access-exp}") long accessExpMin,
            @Value("${jwt.refresh-exp}") long refreshExpMin
    ) {
        this.key = Jwts.SIG.HS256.key().build();
        this.accessExpMin = accessExpMin;
        this.refreshExpMin = refreshExpMin;
    }

    public String createAccessToken(String username, String role) {
        return createToken("access", accessExpMin, username, role);
    }

    public String createRefreshToken(String username) {
        return createToken("refresh", refreshExpMin, username, null);
    }

    private String createToken(String subject, long expMin, String username, String role) {

        Date expire = new Date(System.currentTimeMillis() + 1000L * 60 * expMin);

        return Jwts.builder()
                .subject(subject)
                .claim("username", username)
                .claim("role", role)
                .expiration(expire)
                .signWith(key)
                .compact();
    }

    public Claims getClaims(String token) {
        JwtParser parser = Jwts.parser().verifyWith(key).build();
        Jws<Claims> jws = parser.parseSignedClaims(token);
        return jws.getPayload();
    }
}
