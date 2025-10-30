package org.example.project.config.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.project.config.security.CustomUserDetails;
import org.example.project.config.security.JwtProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final String ACCESS_COOKIE = "accessToken";
    private static final String REFRESH_COOKIE = "refreshToken";

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String accessToken = readCookie(request, ACCESS_COOKIE).orElse(null);

        log.debug("[JWT] 요청 URI: {} / AccessToken 존재: {}", request.getRequestURI(), accessToken != null);

        if (accessToken != null && jwtProvider.validateToken(accessToken)) {
            setAuthenticationFromToken(accessToken);
            chain.doFilter(request, response);
            return;
        }

        String refreshToken = readCookie(request, REFRESH_COOKIE).orElse(null);
        if (refreshToken != null && jwtProvider.validateToken(refreshToken)) {
            try {
                CustomUserDetails user = jwtProvider.getUserDetails(refreshToken);
                String newAccess = jwtProvider.createAccessToken(user);

                writeCookie(response, ACCESS_COOKIE, newAccess, 60 * 15);

                setAuthentication(user);

                log.debug("[JWT] Access 재발급 성공 -> {}", user.getEmail());
            } catch (Exception e) {
                log.warn("[JWT] Refresh 처리 중 오류: {}", e.getMessage());
                clearAuthCookies(response);
            }
        } else {
            clearAuthCookies(response);
        }

        chain.doFilter(request, response);
    }


    private void setAuthenticationFromToken(String token) {
        CustomUserDetails user = jwtProvider.getUserDetails(token);
        setAuthentication(user);
    }

    private void setAuthentication(CustomUserDetails user) {
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private Optional<String> readCookie(HttpServletRequest request, String name) {
        if (request.getCookies() == null) return Optional.empty();
        return Arrays.stream(request.getCookies())
                .filter(c -> name.equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }

    private void writeCookie(HttpServletResponse response, String name, String value, int maxAgeSec) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // TODO: TRUE
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeSec);
        response.addCookie(cookie);
    }

    private void clearAuthCookies(HttpServletResponse response) {
        expireCookie(response, ACCESS_COOKIE);
        expireCookie(response, REFRESH_COOKIE);
    }

    private void expireCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // TODO: TRUE
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
