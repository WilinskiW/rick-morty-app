package com.rick_morty.rick_morty_security.service;

import com.rick_morty.rick_morty_security.dto.UserCredential;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    void registerUser(String username, String password);
    String verifyCredentials(UserCredential user);
    void createJwtCookie(HttpServletResponse response, String token);
    void logout(HttpServletResponse response);
}
