package com.rick_morty.rick_morty_security.service;

import com.rick_morty.rick_morty_security.dto.UserCredential;

public interface UserService {
    void registerUser(String username, String password);

    String verify(UserCredential user);
}
