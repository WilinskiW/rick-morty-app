package com.rick_morty.rick_morty_ui.controller;

import com.rick_morty.rick_morty_security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "auth/register";
    }

    @GetMapping("/register?error")
    public String showRegistrationFormWithError() {
        return "auth/register";
    }

    @GetMapping("/account")
    public String showAccountPage() {
        return "auth/account";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            userService.registerUser(username, password);
        }
        catch (IllegalArgumentException e){
            model.addAttribute("error", true);
            return "auth/register";
        }
        return "redirect:/login";
    }
}
