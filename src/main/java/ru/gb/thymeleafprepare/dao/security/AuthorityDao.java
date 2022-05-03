package ru.gb.thymeleafprepare.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.thymeleafprepare.entity.security.Authority;

public interface AuthorityDao extends JpaRepository<Authority, Long> {
}
