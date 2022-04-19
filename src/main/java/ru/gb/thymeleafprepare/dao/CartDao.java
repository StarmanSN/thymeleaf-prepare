package ru.gb.thymeleafprepare.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.thymeleafprepare.entity.Cart;

public interface CartDao extends JpaRepository<Cart, Long> {

    Cart save(Cart cart);
}
