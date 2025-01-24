package com.rick_morty.rick_morty_security;

import com.rick_morty.rick_morty_data.model.User;
import com.rick_morty.rick_morty_data.repository.security.UserRepository;
import com.rick_morty.rick_morty_security.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = com.rick_morty.rick_morty_security.RickMortySecurityApplication.class)
public class CustomUserDetailsServiceTest {
    @InjectMocks
    private CustomUserDetailsService service;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("1234");
        user.setRoles(Set.of("USER"));
        
        userRepository.save(user);
    }

    @Test
    void loadUserByUsernameByValidCredentialsTest(){
        User testUser = new User();
        testUser.setUsername("test");
        testUser.setPassword("1234");
        testUser.setRoles(Set.of("USER"));

        when(userRepository.findUserByUsername("test")).thenReturn(Optional.of(testUser));

        UserDetails userDetails = service.loadUserByUsername("test");
        assertEquals("test",userDetails.getUsername());
        assertEquals("1234",userDetails.getPassword());
        Set<String> actualAuthorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        assertEquals(Set.of("ROLE_USER"), actualAuthorities);
    }

    @Test
    void loadUserByUsernameByIncorrectCredentialsTest(){
        when(userRepository.findUserByUsername("test")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("test"));
    }
}
