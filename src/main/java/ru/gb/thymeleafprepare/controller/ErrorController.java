package ru.gb.thymeleafprepare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    public String getErrorPath() {
        return "Страница не найдена";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}