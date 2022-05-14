package ru.gb.thymeleafprepare.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.thymeleafprepare.entity.security.AccountRole;

public interface AccountRoleDao extends JpaRepository<AccountRole, Long> {
}
