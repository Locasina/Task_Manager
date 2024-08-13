package ru.crm.taskboard.web.service;

import com.google.common.base.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.crm.taskboard.data.dto.AccountData;
import ru.crm.taskboard.data.dto.response.CustomPage;
import ru.crm.taskboard.data.entities.Account;
import ru.crm.taskboard.data.repositories.AccountRepository;
import ru.crm.taskboard.data.services.CommonConverterService;
import ru.crm.taskboard.web.dto.CreateAccountRequest;
import ru.crm.taskboard.web.dto.UpdateAccountRequest;
import ru.crm.taskboard.web.exceptions.ResourceAlreadyInUseException;
import ru.crm.taskboard.web.exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static ru.crm.taskboard.data.utils.DataUtils.checkAndSetValue;

@Service
public class AccountService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AccountRepository accountRepository;
    private final CommonConverterService commonConverterService;

    public AccountService(BCryptPasswordEncoder bCryptPasswordEncoder,
                          AccountRepository accountRepository,
                          CommonConverterService commonConverterService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.accountRepository = accountRepository;
        this.commonConverterService = commonConverterService;
    }

    public Account getAccount(Integer id) throws UserNotFoundException {
        return accountRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("Пользователь с идентификатором: %s не был найден!", id)));
    }

    public AccountData getAccountData(Integer accountId) throws UserNotFoundException {
        Account account = getAccount(accountId);

        return commonConverterService.formAccountData(account);
    }

    public List<Account> getAccounts(Collection<Integer> accountIds) {
        return accountRepository.findAllById(accountIds);
    }

    public List<AccountData> getAccountsData(Collection<Integer> accountIds) throws UserNotFoundException {
        List<AccountData> accountDataList = new ArrayList<>();

        for (Integer accountId : accountIds) {
            accountDataList.add(getAccountData(accountId));
        }

        return accountDataList;
    }

    public boolean checkAccountExistence(Integer accountId) {
        Objects.requireNonNull(accountId);

        return accountRepository.existsById(accountId);
    }

    public CustomPage getAccountDataList(Pageable pageable) {
        Page<Account> accounts =
                accountRepository
                        .findAll(pageable);
        List<AccountData> accountDataList = new ArrayList<>();

        for (Account account : accounts) {

            accountDataList.add(commonConverterService.formAccountData(account));
        }

        return new CustomPage(accounts, accountDataList);
    }

    public CustomPage createAccount(CreateAccountRequest request,
                                    Pageable pageable) throws ResourceAlreadyInUseException {
        checkAccountExistenceByLogin(request.getLogin());
        checkAccountExistenceByEmail(request.getEmail());

        Account account = new Account();
        String personFullName = String.format("%s %s %s", request.getPersonSurname(), request.getPersonName(), request.getPersonPatronymic());

        account.setPersonName(request.getPersonName());
        account.setPersonSurname(request.getPersonSurname());
        account.setLogin(request.getLogin());
        account.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        account.setEmail(request.getEmail());
        account.setEnabled(request.getEnabled());
        account.setLastTimeActive(LocalDateTime.now());

        accountRepository.save(account);

        return getAccountDataList(pageable);
    }

    private void checkAccountExistenceByLogin(String login) throws ResourceAlreadyInUseException {
        if (accountRepository.existsByLogin(login)) {
            throw new ResourceAlreadyInUseException(String.format("Аккаунт пользователя с логином: %s уже существует!", login));
        }
    }

    private void checkAccountExistenceByEmail(String email) throws ResourceAlreadyInUseException {
        if (accountRepository.existsByEmail(email)) {
            throw new ResourceAlreadyInUseException(String.format("Аккаунт пользователя с адресом эл. почты: %s уже существует!", email));
        }
    }

    public CustomPage updateAccount(UpdateAccountRequest request,
                                    Pageable pageable) throws UserNotFoundException, ResourceAlreadyInUseException {
        Account account = getAccount(request.getAccountId());

        checkAndSetLogin(account, request.getLogin());
        checkAndSetEmail(account, request.getEmail());
        checkAndSetValue(account.getPassword(), !Strings.isNullOrEmpty(request.getPassword()) ? bCryptPasswordEncoder.encode(request.getPassword()) : null, account::setPassword);
        checkAndSetValue(account.getPersonName(), request.getPersonName(), account::setPersonName);
        checkAndSetValue(account.getPersonSurname(), request.getPersonSurname(), account::setPersonSurname);
        checkAndSetValue(account.getEnabled(), request.getEnabled(), account::setEnabled);

        accountRepository.save(account);

        return getAccountDataList(pageable);
    }

    private void checkAndSetLogin(Account account, String login) throws ResourceAlreadyInUseException {
        if (!Strings.isNullOrEmpty(login)) {
            if (accountRepository.existsByLoginAndIdNot(login, account.getId())) {
                throw new ResourceAlreadyInUseException(String.format("Аккаунт пользователя с логином: %s уже существует!", login));
            } else {
                account.setLogin(login);
            }
        }
    }

    private void checkAndSetEmail(Account account, String email) throws ResourceAlreadyInUseException {
        if (!Strings.isNullOrEmpty(email)) {
            if (accountRepository.existsByEmailAndIdNot(email, account.getId())) {
                throw new ResourceAlreadyInUseException(String.format("Аккаунт пользователя с адресом эл. почты: %s уже существует!", email));
            } else {
                account.setEmail(email);
            }
        }
    }

    public CustomPage deleteAccount(Integer accountId,
                                    Pageable pageable) {
        if (accountRepository.existsById(accountId)) {
            accountRepository.deleteById(accountId);
        }

        return getAccountDataList(pageable);
    }
}
