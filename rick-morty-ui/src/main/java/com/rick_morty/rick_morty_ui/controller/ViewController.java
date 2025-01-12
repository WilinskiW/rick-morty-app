package com.rick_morty.rick_morty_ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String showHomePage(){
        return "homepage";
    }
}
