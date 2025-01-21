package com.rick_morty.rick_morty_security.service;

import com.rick_morty.rick_morty_data.model.Authority;
import com.rick_morty.rick_morty_data.model.User;
import com.rick_morty.rick_morty_data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(appendRoles(user.getAuthorities()))
                .build();
    }

    private String appendRoles(Set<Authority> authoritySet){
        StringBuilder roles = new StringBuilder();
        for (Authority authority : authoritySet) {
            roles.append(authority.getAuthority()).append(",");
        }
        return roles.toString();
    }
}
