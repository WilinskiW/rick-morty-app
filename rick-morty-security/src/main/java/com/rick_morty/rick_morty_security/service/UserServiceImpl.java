package com.rick_morty.rick_morty_security.service;

import com.rick_morty.rick_morty_data.model.User;
import com.rick_morty.rick_morty_data.repository.security.UserRepository;
import com.rick_morty.rick_morty_security.dto.TokenInfo;
import com.rick_morty.rick_morty_security.dto.UserCredential;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JWTService jwtService;


    @Override
    @Transactional
    public void registerUser(String username, String password) {
        verifyRegisterForm(username, password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.getRoles().add("USER");
        userRepository.save(user);
    }

    private void verifyRegisterForm(String username, String password){
        if (username.isBlank() || password.isBlank()) {
            throw new IllegalArgumentException("Username or password are blank");
        }

        if (username.length() < 4 || username.length() > 255) {
            throw new IllegalArgumentException("Username is too long or too short");
        }

        if (password.length() < 4 || password.length() > 255) {
            throw new IllegalArgumentException("Password is too long or too short");
        }

        if(userRepository.findUserByUsername(username).isPresent()){
            throw new IllegalArgumentException("Username is already in use");
        }
    }

    @Override
    public String verifyCredentials(UserCredential user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.username(),
                user.password()
        ));

        return authentication.isAuthenticated() ? generateToken(user.username()) : "Failure";
    }

    private String generateToken(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);

        if (user.isEmpty()) {
            throw new IllegalCallerException();
        }

        TokenInfo tokenInfo = new TokenInfo(
                user.get().getId().toString(),
                user.get().getUsername(),
                user.get().getRoles()
        );

        return jwtService.generateToken(tokenInfo);
    }

    @Override
    public void createJwtCookie(HttpServletResponse response, String verifiedOutcome) {
        Cookie cookie = new Cookie("jwt", verifiedOutcome);
        cookie.setHttpOnly(true);   // Helps with XSS
        cookie.setSecure(true);     // HTTPS Only
        cookie.setPath("/");        // Access by whole application
        cookie.setMaxAge(3600);     // Expire after one hour
        cookie.setAttribute("SameSite", "Strict");  // Protects from CSRF

        response.addCookie(cookie);
    }

    @Override
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Set to 0 for delete

        response.addCookie(cookie);
    }
}