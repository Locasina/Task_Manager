package ru.crm.taskboard.data.services;

import org.springframework.stereotype.Service;
import ru.crm.taskboard.data.dto.AccountData;
import ru.crm.taskboard.data.entities.Account;

import java.util.Objects;

@Service
public class CommonConverterService {

    public AccountData formAccountData(Account account) {
        if (Objects.isNull(account)) {
            return null;
        }

        AccountData accountData = new AccountData();

        accountData.setAccountId(account.getId());
        accountData.setPersonName(account.getPersonName());
        accountData.setPersonSurname(account.getPersonSurname());
        accountData.setLogin(account.getLogin());
        accountData.setEmail(account.getEmail());
        accountData.setEnabled(account.getEnabled());
        accountData.setLastTimeActive(account.getLastTimeActive());
        accountData.setCreationTime(account.getCreationTime());
        accountData.setUpdateTime(account.getUpdateTime());

        return accountData;
    }
}