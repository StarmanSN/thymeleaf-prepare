package ru.gb.thymeleafprepare;

import org.springframework.web.bind.annotation.*;
import ru.gb.thymeleafprepare.entity.Product;
import ru.gb.thymeleafprepare.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

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

    @PostMapping
    public String saveProduct(Product product) {
        product.setManufactureDate(LocalDate.now());
        productService.save(product);
        return "redirect:/product/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable(name = "id") Long id) {
        productService.deleteById(id);
        return "redirect:/product/all";
    }

//    @GetMapping("/delete")
//    public String deleteById(@RequestParam(name = "id") Long id) {
//        productService.deleteById(id);
//        return "redirect:/product/all";
//    }

    @GetMapping("/cart")
    public String cartList(Model model) {
        model.addAttribute("cart", productService.findAllInCart());
        return "cart-list";
    }

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable(name = "id") Long id) {
        productService.addToCart(id);
        return "redirect:/product/all";
    }

    @GetMapping("/deleteCart/{id}")
    public String deleteFromCart(@PathVariable(name = "id") Long id) {
        productService.deleteFromCart(id);
        return "redirect:/product/cart-list";
    }
}
