package com.rick_morty.rick_morty_ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class ViewController {
    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }
}
