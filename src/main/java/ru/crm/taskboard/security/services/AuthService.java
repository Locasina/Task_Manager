package ru.crm.taskboard.security.services;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.crm.taskboard.data.entities.Account;
import ru.crm.taskboard.data.entities.TokenBlacklist;
import ru.crm.taskboard.data.repositories.AccountRepository;
import ru.crm.taskboard.data.repositories.TokenBlacklistRepository;
import ru.crm.taskboard.security.dto.LoginRequest;
import ru.crm.taskboard.security.dto.LogoutRequest;
import ru.crm.taskboard.security.dto.UserDetailsImpl;
import ru.crm.taskboard.security.exceptions.InvalidPasswordException;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Slf4j
@Service
public class AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDetailsServiceImpl userDetailsService;
    private final TokenBlacklistRepository tokenBlacklistRepository;
    private final AccountRepository accountRepository;
    private final TokenService tokenService;

    public AuthService(BCryptPasswordEncoder bCryptPasswordEncoder,
                       UserDetailsServiceImpl userDetailsService,
                       TokenBlacklistRepository tokenBlacklistRepository,
                       AccountRepository accountRepository,
                       TokenService tokenService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService = userDetailsService;
        this.tokenBlacklistRepository = tokenBlacklistRepository;
        this.accountRepository = accountRepository;
        this.tokenService = tokenService;
    }

    public String login(LoginRequest request) throws InvalidPasswordException {
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(request.getLogin());

        if (bCryptPasswordEncoder.matches(request.getPassword(), userDetails.getPassword()) && userDetails.getAccount().getEnabled()) {
            Account account = accountRepository.findById(userDetails.getId()).orElse(null);

            if (Objects.nonNull(account)) {
                account.setLastTimeActive(LocalDateTime.now());

                accountRepository.save(account);
            }

            return tokenService.generateToken(userDetails);
        } else {
            throw new InvalidPasswordException("Был введён неверный пароль!");
        }
    }

    public String refreshToken(String refreshToken) throws LoginException {
        Claims claims = null;
        
        try {
            claims = tokenService.getClaimsForToken(refreshToken);
            if (!claims.get("type").equals("authToken")) {
                throw new Error("Токен не авторизации");
            }
        } catch (Error error) {
            log.error(String.format("Не удалось расшифровать токен", refreshToken), error);
            throw new LoginException("Не удалось расшифровать токен");
        }

        if (!tokenService.verifyExpiration(claims.getExpiration())) {
            throw new LoginException("Время жизни токена обновления истекло");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(claims.getSubject());

        return tokenService.generateToken(userDetails);
    }

    public void logout(LogoutRequest request) {
        String tokenWithoutPrefix = request.getToken().replace("Bearer ", "");
        Claims tokenClaims = tokenService.getClaimsForToken(tokenWithoutPrefix);
        TokenBlacklist tokenBlacklist = tokenBlacklistRepository.findByToken(tokenWithoutPrefix).orElse(null);

        if (Objects.isNull(tokenBlacklist)) {
            tokenBlacklist = new TokenBlacklist();

            tokenBlacklist.setToken(tokenWithoutPrefix);
            tokenBlacklist.setExpirationTime(LocalDateTime.ofInstant(tokenClaims.getExpiration().toInstant(), ZoneId.systemDefault()));

            tokenBlacklistRepository.save(tokenBlacklist);
        }
    }
}
