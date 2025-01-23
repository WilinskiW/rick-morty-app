package com.rick_morty.rick_morty_ui.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class ViewControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        ViewController viewController = new ViewController();
        mockMvc = MockMvcBuilders.standaloneSetup(viewController).build();
    }

    @Test
    void testShowHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}