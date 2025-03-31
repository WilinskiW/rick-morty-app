package com.rick_morty.rick_morty_security.controller;

import com.rick_morty.rick_morty_data.model.User;
import com.rick_morty.rick_morty_data.repository.security.UserRepository;
import com.rick_morty.rick_morty_security.dto.TokenInfo;
import com.rick_morty.rick_morty_security.dto.UserCredential;
import com.rick_morty.rick_morty_security.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final UserRepository userRepository;

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

    @GetMapping("/userInfo")
    public ResponseEntity<TokenInfo> getCurrentUser(Authentication authentication){
        if(authentication == null || !authentication.isAuthenticated()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userRepository.findUserByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        TokenInfo tokenInfo = new TokenInfo(
                user.getId().toString(),
                user.getUsername(),
                user.getRoles()
        );

        return ResponseEntity.ok(tokenInfo);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response){
        userService.logout(response);
        return ResponseEntity.ok().build();
    }
}
