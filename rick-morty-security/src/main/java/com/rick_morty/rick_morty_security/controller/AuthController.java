package com.rick_morty.rick_morty_security.controller;

import com.rick_morty.rick_morty_security.dto.UserCredential;
import com.rick_morty.rick_morty_security.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public void registerUser(@RequestBody UserCredential user) {
        userService.registerUser(user.username(), user.password());
    }


    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody UserCredential user, HttpServletResponse response) {
        String verifiedOutcome = userService.verifyCredentials(user);

        if(verifiedOutcome.contains("Failure")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        userService.createJwtCookie(response, verifiedOutcome);

        return ResponseEntity.ok().build();
    }
}
