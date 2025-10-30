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

    private static final long EXPIRATION_MS = 1000L * 60 * 15;
    private static final long EXPIRATION_REFRESH_MS = 1000L * 60 * 60 * 24;

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            log.warn("[JWT] Invalid token: {}", e.getMessage());
            return false;
        }
    }

    public String getEmail(String token) {
        return Jwts.parser().verifyWith(KEY).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Role getRole(String token) {
        String roleStr = Jwts.parser().verifyWith(KEY).build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
        return Role.valueOf(roleStr);
    }

    public String createAccessToken(CustomUserDetails user) {
        log.info("[JWT] Creating access token for user: {}", user.toString());
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .claim("name", user.getName())
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(KEY)
                .compact();
    }

    public CustomUserDetails getUserDetails(String token) {
        var claims = Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token).getPayload();

        Long id = claims.get("id", Long.class);
        String name = claims.get("name", String.class);
        String email = claims.getSubject();
        Role role = Role.valueOf(claims.get("role", String.class));

        var user = new org.example.project.model.User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);

        return new CustomUserDetails(user);
    }

    public Long getUserId(String token) {
        return Jwts.parser()
                .verifyWith(KEY).build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", Long.class);
    }

    public String getName(String token) {
        return Jwts.parser()
                .verifyWith(KEY).build()
                .parseSignedClaims(token)
                .getPayload()
                .get("name", String.class);
    }

    public String createRefreshToken(CustomUserDetails user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("id", user.getId())
                .claim("name", user.getName())
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_REFRESH_MS))
                .signWith(KEY)
                .compact();
    }
}
