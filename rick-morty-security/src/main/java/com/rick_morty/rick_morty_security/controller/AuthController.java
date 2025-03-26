package com.rick_morty.rick_morty_security.controller;

import com.rick_morty.rick_morty_security.dto.UserCredential;
import com.rick_morty.rick_morty_security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


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
    public ResponseEntity<Map<String, String>> login(@RequestBody UserCredential user) {
        String verifiedOutcome = userService.verify(user);

        if(verifiedOutcome.contains("Failure")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Unauthorized"));
        }

        Map<String, String> response = new HashMap<>();
        response.put("token", verifiedOutcome);
        return ResponseEntity.ok(response);
    }
}
