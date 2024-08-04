package ru.crm.taskboard.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.crm.taskboard.data.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByLogin(String login);

    Account findByEmail(String email);

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

    boolean existsByLoginAndIdNot(String login, Integer id);

    boolean existsByEmailAndIdNot(String email, Integer id);
}
