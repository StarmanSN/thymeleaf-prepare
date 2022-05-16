package ru.gb.thymeleafprepare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.thymeleafprepare.entity.Product;
import ru.gb.thymeleafprepare.service.ProductService;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public String getProductList(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product-list";
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('product.create', 'product.update')")
    public String showForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        Product product;
        if (id != null) {
            product = productService.findById(id);
        } else {
            product = new Product();
        }
        model.addAttribute("product", product);
        return "product-form";
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('product.read')")
    public String showInfo(Model model, @PathVariable(name = "productId") Long id) {
        Product product;
        if (id != null) {
            product = productService.findById(id);
        } else {
            return "redirect:/product/all";
        }
        model.addAttribute("product", product);
        return "product-info";
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('product.create', 'product.update')")
    public String saveProduct(Product product) {
        product.setManufactureDate(LocalDate.now());
        productService.save(product);
        return "redirect:/product/all";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('product.delete')")
    public String deleteById(@PathVariable(name = "id") Long id) {
        productService.deleteById(id);
        return "redirect:/product/all";
    }

    @GetMapping("/cart-list")
    @PreAuthorize("hasAnyAuthority('product.add')")
    public String cartList(Model model) {
        model.addAttribute("cart", productService.findProductsInCart());
        return "cart-list";
    }

    @GetMapping("/cart/add/{id}")
    @PreAuthorize("hasAnyAuthority('product.add')")
    public String addToCart(@PathVariable(name = "id") Long id) {
        productService.addToCart(id);
        return "redirect:/product/all";
    }

    @GetMapping("/deleteCart/{id}")
    @PreAuthorize("hasAnyAuthority('product.add')")
    public String deleteFromCart(@PathVariable(name = "id") Long id) {
        productService.deleteFromCart(id);
        return "redirect:/product/cart-list";
    }

    @GetMapping("/message")
    public String needToAuth() {
        return "message";
    }
}
