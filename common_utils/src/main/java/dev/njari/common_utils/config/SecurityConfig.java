package dev.njari.common_utils.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author njari_mathenge
 * on 24/02/2024.
 * github.com/iannjari
 */

@Configuration
public class SecurityConfig {

    public static final String[] PUBLIC_ENDPOINTS = {"/management/*"};

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .cors().and().authorizeHttpRequests()
                .requestMatchers(PUBLIC_ENDPOINTS).permitAll();

        return httpSecurity.build();
    }
}
