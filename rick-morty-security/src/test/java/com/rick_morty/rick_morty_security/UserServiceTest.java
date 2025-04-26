package com.rick_morty.rick_morty_security;

import com.rick_morty.rick_morty_data.model.User;
import com.rick_morty.rick_morty_data.repository.security.UserRepository;
import com.rick_morty.rick_morty_security.dto.UserCredential;
import com.rick_morty.rick_morty_security.service.JWTService;
import com.rick_morty.rick_morty_security.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authManager;
    @Mock
    private JWTService jwtService;
    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void registerUser_ShouldSaveUser_WhenInputIsValid() {
        String username = "validUser";
        String password = "validPass";
        String encodedPassword = "encodedPass";

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        userService.registerUser(username, password);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals(username, savedUser.getUsername());
        assertEquals(encodedPassword, savedUser.getPassword());
        assertTrue(savedUser.getRoles().contains("USER"));
    }

    @Test
    void registerUser_ShouldThrowException_WhenUsernameIsBlank() {
        String username = " ";
        String password = "validPass";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser(username, password));
        assertEquals("Username or password are blank", exception.getMessage());
    }

    @Test
    void registerUser_ShouldThrowException_WhenPasswordIsBlank() {
        String username = "validUser";
        String password = " ";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser(username, password));
        assertEquals("Username or password are blank", exception.getMessage());
    }

    @Test
    void registerUser_ShouldThrowException_WhenUsernameIsTooShort() {
        String username = "usr";
        String password = "validPass";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser(username, password));
        assertEquals("Username is too long or too short", exception.getMessage());
    }

    @Test
    void registerUser_ShouldThrowException_WhenUsernameIsTooLong() {
        String username = "a".repeat(256);
        String password = "validPass";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser(username, password));
        assertEquals("Username is too long or too short", exception.getMessage());
    }

    @Test
    void registerUser_ShouldThrowException_WhenPasswordIsTooShort() {
        String username = "validUser";
        String password = "123";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser(username, password));
        assertEquals("Password is too long or too short", exception.getMessage());
    }

    @Test
    void registerUser_ShouldThrowException_WhenPasswordIsTooLong() {
        String username = "validUser";
        String password = "a".repeat(256);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser(username, password));
        assertEquals("Password is too long or too short", exception.getMessage());
    }

    @Test
    void registerUser_ShouldThrowException_WhenUserAlreadyExists() {
        String username = "validUser";
        String password = "validPass";

        when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(new User()));

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(username, password));
    }

    @Test
    void verifyCredentials_successfulAuthentication_returnsToken() {
        UserCredential userCredential = new UserCredential("testUser", "password");
        User testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUser");
        testUser.setPassword("password");
        testUser.setRoles(Set.of("USER, ADMIN"));

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(userRepository.findUserByUsername("testUser"))
                .thenReturn(Optional.of(testUser)); // Dummy user

        when(jwtService.generateToken(any()))
                .thenReturn("mocked-jwt-token");

        String token = userService.verifyCredentials(userCredential);

        assertEquals("mocked-jwt-token", token);
        verify(authManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(any());
    }

    @Test
    void verifyCredentials_authenticationFails_returnsFailureString() {
        UserCredential userCredential = new UserCredential("testUser", "wrongPassword");

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        String result = userService.verifyCredentials(userCredential);

        assertEquals("Failure", result);
    }
}
