package com.rick_morty.rick_morty_security.config;

import com.rick_morty.rick_morty_security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> corsConfigurationSource());
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/character/add", "/location/add", "/episode/add",
//                                "/character/delete/**", "/location/delete/**", "/episode/delete/**").hasRole("ADMIN")
//                        .requestMatchers("/character/edit/**", "/episode/edit/**",
//                                "/location/edit/**").hasAnyRole("MODERATOR", "ADMIN")
//                        .requestMatchers("/", "/error/**", "auth/register", "auth/login",
//                                "/character/all", "/location/all", "/episode/all").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/character/{id}", "/episode/{id}",
//                                "/location/{id}").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/register",
//                                "/authenticateTheUser").permitAll()
//                        .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico",
//                                "/static/**").permitAll()
//                        .requestMatchers("/api/**").authenticated()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/auth/login")
//                        .loginProcessingUrl("/authenticateTheUser")
//                        .defaultSuccessUrl("/", true)
//                        .failureHandler(new CustomAuthenticationFailureHandler())
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .permitAll()
//                )
//                .exceptionHandling(ex ->
//                        ex.accessDeniedPage("/error/403"));
        return http.build();
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:4200");
        config.setAllowCredentials(true);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of(HttpHeaders.CONTENT_TYPE, HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
