package com.rick_morty.rick_morty_security.service;

import com.rick_morty.rick_morty_data.model.User;
import com.rick_morty.rick_morty_data.repository.security.UserRepository;
import com.rick_morty.rick_morty_security.dto.UserCredential;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        if(username.isBlank() || password.isBlank()){
            throw new IllegalArgumentException("Username or password are blank");
        }

        if(username.length() < 4 || username.length() > 255){
            throw new IllegalArgumentException("Username is too long or too short");
        }

        if(password.length() < 4 || password.length() > 255){
            throw new IllegalArgumentException("Password is too long or too short");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.getRoles().add("USER");
        userRepository.save(user);
    }

    @Override
    public String verify(UserCredential user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.username(),
                user.password()
        ));
        return authentication.isAuthenticated() ? generateToken() : "Failure";
    }

    private String generateToken() {
        return jwtService.generateToken();
    }
}
