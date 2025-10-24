package org.example.project.config.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.example.project.model.Role;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private static final String SECRET = "A1B2C3D4E5F6G7H8I9J0K1L2M3N4O5P6";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    private static final long EXPIRATION_MS = 1000L * 60 * 60 * 24;
    private static final long EXPIRATION_REFRESH_MS = 1000L * 60 * 60 * 24 * 7;

    public boolean validateToken(String token) {
        log.debug("Validating token: {}", token);
        try {
            Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token).getPayload();
            return true;
        } catch (Exception e) {
            log.error("Invalid token received: {}", e.getMessage());
            return false;
        }
    }

    public String getEmail(String token) {
        return Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public Role getRole(String token) {
        return Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token).getPayload().get("role", Role.class);
    }

    public String createAccessToken(String email, Role role) {
        log.debug("Creating access token for user: {}", email);
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(KEY)
                .compact();
    }

    public String createRefreshToken(String email) {
        log.debug("Creating refresh token for user: {}", email);
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_REFRESH_MS))
                .signWith(KEY)
                .compact();
    }
}
