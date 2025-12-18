package com.gt.planit.security.config;

import com.gt.planit.security.jwt.JWTUtil;
import com.gt.planit.security.jwt.JwtAuthenticationFilter;
import com.gt.planit.security.jwt.JwtAuthorizationFilter;
import com.gt.planit.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTUtil jwtUtil;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationFilter loginFilter =
                new JwtAuthenticationFilter(authenticationManager(), jwtUtil);
        loginFilter.setFilterProcessesUrl("/login");

        JwtAuthorizationFilter authorizationFilter =
                new JwtAuthorizationFilter(jwtUtil, userDetailsService);

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/check-email").permitAll()
                .requestMatchers(HttpMethod.GET, "/check-nickname").permitAll()
                .requestMatchers(HttpMethod.POST, "/join").permitAll()
                .requestMatchers("/login", "/auth/refresh").permitAll()
                .requestMatchers(HttpMethod.GET, "/v1/spots").permitAll()
                .requestMatchers(HttpMethod.GET, "/v1/regions").permitAll()
                .anyRequest().authenticated()
        );

        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.formLogin(form -> form.disable());

        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
