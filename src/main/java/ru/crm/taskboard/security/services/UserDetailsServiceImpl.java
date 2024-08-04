package ru.crm.taskboard.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.crm.taskboard.data.entities.Account;
import ru.crm.taskboard.data.repositories.AccountRepository;
import ru.crm.taskboard.security.dto.UserDetailsImpl;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    public UserDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Account account = accountRepository.findByLogin(login);

        if (Objects.isNull(account)) {
            account = accountRepository.findByEmail(login);

            if (Objects.isNull(account)) {
                throw new UsernameNotFoundException(String.format("Пользователь с логином: %s не был найден!", login));
            }
        }

        return new UserDetailsImpl(account);
    }
}
