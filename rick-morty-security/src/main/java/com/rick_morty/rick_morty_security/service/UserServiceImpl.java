package com.rick_morty.rick_morty_security.service;

import com.rick_morty.rick_morty_data.model.User;
import com.rick_morty.rick_morty_data.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
}
