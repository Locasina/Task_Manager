package ru.crm.taskboard.security.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.crm.taskboard.security.services.TokenService;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class TokenAuthFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (tokenService.checkToken(request)) {
                String token = tokenService.getAndClearToken(request);
                Claims claims = tokenService.getClaimsForToken(token);

                if (Objects.nonNull(claims.get("role")) && tokenService.checkTokenBlacklisted(token)) {
                    tokenService.authenticateUser(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            log.error("Произошла ошибка во время попытки аутентификации запроса!", ex);

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());

            SecurityContextHolder.clearContext();
        } catch (UnsupportedJwtException | MalformedJwtException ex) {
            log.error("Произошла ошибка во время попытки аутентификации запроса!", ex);

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
        }
    }
}
