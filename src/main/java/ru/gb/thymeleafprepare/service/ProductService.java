package ru.gb.thymeleafprepare.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.thymeleafprepare.dao.CartDao;
import ru.gb.thymeleafprepare.dao.ProductDao;
import ru.gb.thymeleafprepare.entity.Cart;
import ru.gb.thymeleafprepare.entity.Product;
import ru.gb.thymeleafprepare.entity.enums.Status;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductDao productDao;
    private final CartDao cartDao;

    @Transactional(propagation = Propagation.NEVER, isolation = Isolation.DEFAULT)
    public long count() {
        System.out.println(productDao.count());
        return productDao.count();
    }

    public Product save(Product product) {
        if (product.getId() != null) {
            Optional<Product> productFromDBOptional = productDao.findById(product.getId());
            if (productFromDBOptional.isPresent()) {
                Product productFromDB = productFromDBOptional.get();
                productFromDB.setTitle(product.getTitle());
                productFromDB.setCost(product.getCost());
                productFromDB.setManufactureDate(product.getManufactureDate());
                productFromDB.setStatus(product.getStatus());
                return productDao.save(productFromDB);
            }
        }
        return productDao.save(product);
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productDao.findById(id).orElse(null);
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Transactional
    public List<Product> findProductsInCart() {
        return cartDao.getById(1L).getProducts();
    }

    public List<Product> findAllActive() {
        return productDao.findAllByStatus(Status.ACTIVE);
    }

    public void deleteById(Long id) {
        try {
            productDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
        }
    }

    public void disable(Long id) {
        Optional<Product> product = productDao.findById(id);
        product.ifPresent(p -> {
            p.setStatus(Status.DISABLED);
            productDao.save(p);
        });
    }

    public Product addToCart(Long id) {
        Cart cart = cartDao.getById(1L);
        Product product = productDao.getById(id);
        cart.addProduct(product);
        cartDao.save(cart);
        return product;
    }

    public void deleteFromCart(Long id) {
        Cart cart = cartDao.getById(1L);
        List<Product> products = cart.getProducts();
        for (int i = 0; i < products.size(); i++) {
            if (products != null) {
                Optional<Product> product = productDao.findById(id);
                products.remove(product);
            }
        }
    }

    public List<Product> findAll(int page, int size) {
        return productDao.findAllByStatus(Status.ACTIVE, PageRequest.of(page, size));
    }

    public List<Product> findAllSortedById() {
        return productDao.findAllByStatus(Status.ACTIVE, Sort.by("id"));
    }

    public List<Product> findAllSortedById(int page, int size) {
        return productDao.findAllByStatus(Status.ACTIVE, PageRequest.of(page, size, Sort.by("id")));
    }
}
