package com.rick_morty.rick_morty_security.service;

import org.springframework.stereotype.Service;

@Service
public class JWTService {

    public String generateToken() {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwibmFtZSI6IkFkYW0gU3RhbG1pcnNraSIsImlhdCI6MTUxNjIzOTAyMn0.VOyPPR-kUOhYAkTvNtXnaoefoHSD7pFDy8AWRwccprw";
    }
}
