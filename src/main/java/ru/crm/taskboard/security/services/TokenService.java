package ru.crm.taskboard.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.crm.taskboard.data.repositories.TokenBlacklistRepository;
import ru.crm.taskboard.security.dto.UserDetailsImpl;

import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class TokenService {

    private final String HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";
    private final TokenBlacklistRepository tokenBlacklistRepository;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.ttl:86400000}")
    private long ttl;
    @Value("${jwt.refreshTtl:172800000}")
    private long refreshTtl;

    public TokenService(TokenBlacklistRepository tokenBlacklistRepository) {
        this.tokenBlacklistRepository = tokenBlacklistRepository;
    }

    public String getAndClearToken(HttpServletRequest request) {
        return request.getHeader(HEADER).replace(TOKEN_PREFIX, "");
    }

    public boolean checkToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HEADER);

        return Objects.nonNull(authenticationHeader) && authenticationHeader.startsWith(TOKEN_PREFIX);
    }

    public boolean checkTokenBlacklisted(String token) {
        return tokenBlacklistRepository.findByToken(token).isEmpty();
    }

    public Claims getClaimsForToken(String token) {
        SecretKeySpec key = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS512.getJcaName());

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String generateToken(UserDetailsImpl userDetails) {
        SecretKeySpec key = getSecretKey();
        String refreshToken =
                Jwts
                        .builder()
                        .setId(userDetails.getId().toString())
                        .setSubject(userDetails.getUsername())
                        .claim("type", "refreshToken")
                        .claim("project", "crm")
                        .setIssuedAt(Date.from(Instant.ofEpochMilli(System.currentTimeMillis())))
                        .setExpiration(Date.from(Instant.ofEpochMilli(System.currentTimeMillis() + refreshTtl)))
                        .signWith(key)
                        .compact();


        String token =
                Jwts
                        .builder()
                        .setId(userDetails.getId().toString())
                        .setSubject(userDetails.getUsername())
                        .claim("username", userDetails.getUsername())
                        .claim("project", "crm")
                        .claim("refreshToken", refreshToken)
                        .claim("type", "authToken")
                        .claim("role", List.of("USER"))
                        .setIssuedAt(Date.from(Instant.ofEpochMilli(System.currentTimeMillis())))
                        .setExpiration(Date.from(Instant.ofEpochMilli(System.currentTimeMillis() + ttl)))
                        .signWith(key)
                        .compact();

        return String.format("Bearer %s", token);
    }

    @SuppressWarnings("unchecked")
    public void authenticateUser(Claims claims) {
        List<String> roles = (List<String>) claims.get("role");
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(claims.getSubject(), claims.getId(), roles.stream().map(SimpleGrantedAuthority::new).toList());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }


    public Boolean verifyExpiration(Date exp) {
        return exp.after(new Date());
    }


    public SecretKeySpec getSecretKey() {
        return new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS512.getJcaName());
    }
}
