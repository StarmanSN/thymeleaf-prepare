package ru.gb.thymeleafprepare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GbHomeController {

    @GetMapping
    public String getHomePage() {
        return "redirect:/product/all";
    }
}
