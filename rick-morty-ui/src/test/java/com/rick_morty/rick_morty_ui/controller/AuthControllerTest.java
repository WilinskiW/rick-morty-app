package com.rick_morty.rick_morty_ui.controller;

import com.rick_morty.rick_morty_security.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testShowLoginPage() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"));
    }

    @Test
    void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/auth/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/register"));
    }

    @Test
    void testShowRegistrationFormWithError() throws Exception {
        mockMvc.perform(get("/auth/register?error"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/register"));
    }

    @Test
    void testShowAccountPage() throws Exception {
        mockMvc.perform(get("/auth/account"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/account"));
    }

    @Test
    void testRegisterUserSuccess() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .param("username", "testuser")
                        .param("password", "testpass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService).registerUser("testuser", "testpass");
    }

    @Test
    void testRegisterUserFailure() throws Exception {
        doThrow(new IllegalArgumentException("Registration failed")).when(userService).registerUser(anyString(), anyString());

        mockMvc.perform(post("/auth/register")
                        .param("username", "testuser")
                        .param("password", "testpass"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/register"))
                .andExpect(model().attribute("error", true));

        verify(userService).registerUser("testuser", "testpass");
    }
}