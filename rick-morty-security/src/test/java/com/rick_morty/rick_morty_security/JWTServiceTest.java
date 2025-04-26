package com.rick_morty.rick_morty_security;

import com.rick_morty.rick_morty_security.dto.TokenInfo;
import com.rick_morty.rick_morty_security.service.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JWTServiceTest {

    private JWTService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JWTService();
    }

    @Test
    void generateToken_shouldReturnValidToken() {
        TokenInfo tokenInfo = new TokenInfo("123", "testuser", Set.of("USER", "ADMIN"));

        String token = jwtService.generateToken(tokenInfo);

        assertNotNull(token);
        assertFalse(token.isEmpty());

        String extractedUsername = jwtService.extractUserName(token);
        assertEquals("testuser", extractedUsername);
    }

    @Test
    void extractUserName_shouldReturnCorrectUsername() {
        TokenInfo tokenInfo = new TokenInfo("456", "anotherUser", Set.of("USER"));
        String token = jwtService.generateToken(tokenInfo);

        String username = jwtService.extractUserName(token);

        assertEquals("anotherUser", username);
    }

    @Test
    void validateToken_shouldReturnTrueForValidToken() {
        TokenInfo tokenInfo = new TokenInfo("789", "validUser", Set.of("USER"));
        String token = jwtService.generateToken(tokenInfo);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("validUser");

        boolean isValid = jwtService.validateToken(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void validateToken_shouldReturnFalseForInvalidUsername() {
        TokenInfo tokenInfo = new TokenInfo("111", "realUser", Set.of("USER"));
        String token = jwtService.generateToken(tokenInfo);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("fakeUser"); // Inny username

        boolean isValid = jwtService.validateToken(token, userDetails);

        assertFalse(isValid);
    }

    @Test
    void isTokenExpired_shouldReturnFalseForNewToken() {
        TokenInfo tokenInfo = new TokenInfo("222", "freshUser", Set.of("USER"));
        String token = jwtService.generateToken(tokenInfo);

        boolean expired = invokeIsTokenExpired(token);

        assertFalse(expired);
    }

    // ====== Pomocnicza metoda ======
    private boolean invokeIsTokenExpired(String token) {
        try {
            var isTokenExpiredMethod = JWTService.class.getDeclaredMethod("isTokenExpired", String.class);
            isTokenExpiredMethod.setAccessible(true);
            return (boolean) isTokenExpiredMethod.invoke(jwtService, token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
