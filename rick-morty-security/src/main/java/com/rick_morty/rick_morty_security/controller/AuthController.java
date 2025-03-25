package com.rick_morty.rick_morty_security.controller;

import com.rick_morty.rick_morty_security.dto.UserCredential;
import com.rick_morty.rick_morty_security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public void registerUser(@RequestBody UserCredential user) {
        userService.registerUser(user.username(), user.password());
        System.out.println("Register:" + user);
    }


    @PostMapping("/login")
    public String login(@RequestBody UserCredential user){
        return userService.verify(user);
    }
}
