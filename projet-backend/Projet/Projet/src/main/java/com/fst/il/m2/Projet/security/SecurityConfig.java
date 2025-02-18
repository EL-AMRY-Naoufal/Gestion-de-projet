package com.fst.il.m2.Projet.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Ensure CORS is configured
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/{id}/password").permitAll() //TODO temporarily
                        .requestMatchers("/api/users/user/**").permitAll() //TODO temporarily
                        .requestMatchers("/api/responsableDepartement/**").hasAuthority("CHEF_DE_DEPARTEMENT")
                        .requestMatchers("/api/users/authenticate").permitAll() // Open login endpoint
                        .requestMatchers("/api/enseignants/enseignants-non-enregistres").permitAll()
                        .requestMatchers("/api/enseignants/**").permitAll()
                        .requestMatchers("/api/categories").permitAll()
                        .requestMatchers("/api/users").permitAll()
                        .requestMatchers(("/api/annees/**")).hasAuthority("CHEF_DE_DEPARTEMENT")
                        .requestMatchers("/api/departements/**").hasAuthority("CHEF_DE_DEPARTEMENT")
                        .requestMatchers("/api/niveaux/**").hasAuthority("CHEF_DE_DEPARTEMENT")
                        .requestMatchers("/api/groupes/**").hasAuthority("CHEF_DE_DEPARTEMENT")
                        .requestMatchers("/api/affectation/**").hasAuthority("CHEF_DE_DEPARTEMENT")



                        //partie creation des affectations
                        .requestMatchers("/api/modules").permitAll()
                        .requestMatchers("/api/responsableDepartement").permitAll()

                        .requestMatchers("/api/annees").permitAll()


                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Correctly use allowedOriginPatterns
        configuration.setAllowedOriginPatterns(List.of("http://localhost:4200")); // Replace with your frontend's actual origin

        // Specify allowed HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allow specific headers or use "*"
        configuration.setAllowedHeaders(List.of("*"));

        // Enable credentials support
        configuration.setAllowCredentials(false);

        // Map this configuration to all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


}
