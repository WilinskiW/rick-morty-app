package com.rick_morty.rick_morty_security.dto;

import java.util.Set;

public record TokenInfo(String id, String username, Set<String> roles) { }