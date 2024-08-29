package ru.crm.taskboard.data.services;

import org.springframework.stereotype.Service;
import ru.crm.taskboard.data.dto.AccountData;
import ru.crm.taskboard.data.dto.TaskBoardData;
import ru.crm.taskboard.data.entities.Account;
import ru.crm.taskboard.data.entities.TaskBoard;

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

    public TaskBoardData formTaskBoardData(TaskBoard taskBoard) {
        if (Objects.isNull(taskBoard)) return null;

        TaskBoardData taskBoardData = new TaskBoardData();

        taskBoardData.setTaskBoardId(taskBoard.getId());
        taskBoardData.setTitle(taskBoard.getTitle());
        taskBoardData.setDescription(taskBoard.getDescription());
        taskBoardData.setCreateDate(taskBoard.getCreateDate());
        taskBoardData.setIsArchived(taskBoard.getIsArchived());
        taskBoardData.setArchiveDate(taskBoard.getArchiveDate());

        for (Account account : taskBoard.getAccountsSet()) {
            taskBoardData.getAccountsDataList().add(formAccountData(account));
        }

        return taskBoardData;
    }
}