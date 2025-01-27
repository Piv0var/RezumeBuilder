package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

/**
 * Controller for handling web pages.
 * This controller provides mappings for rendering HTML templates in response to specific requests.
 */
@Controller
public class WebController {

    /**
     * Handles the root URL ("/") and returns the "index" page.
     * 
     * @return The name of the HTML file to render (index.html in the templates directory).
     */
    @GetMapping("/")
    public String index() {
        return "index";  // Returns the index.html file from the templates folder
    }

    /**
     * Handles the "/resume" URL and returns the "resume" page.
     *
     * @return The name of the HTML file to render (resume.html in the templates directory).
     */
    @GetMapping("/resume")
    public String showResumePage() {
        return "resume"; // Refers to the resume.html file in the templates folder
    }

    /**
     * Handles the "/login" URL and returns the "login" page.
     *
     * @return The name of the HTML file to render (login.html in the templates directory).
     */
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // The name of the HTML file for the login page
    }

    /**
     * Handles the "/register" URL and returns the "register" page.
     *
     * @return The name of the HTML file to render (register.html in the templates directory).
     */
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // The name of the HTML file for the registration page
    }
}
