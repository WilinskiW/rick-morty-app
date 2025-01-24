package com.rick_morty.rick_morty_security;

import com.rick_morty.rick_morty_data.model.User;
import com.rick_morty.rick_morty_data.repository.security.UserRepository;
import com.rick_morty.rick_morty_security.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
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
}
