package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;


@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index";  // вернёт файл index.html из папки templates
    }
    @GetMapping("/resume")
    public String showResumePage() {
        return "resume"; // Указывает на файл `resume.html` в папке templates
    }
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Здесь "login" — это имя HTML-файла в папке templates.
    }
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // Имя HTML-файла для страницы регистрации
    }

}
