package com.openclassrooms.projet3.excercice1.config;

import com.openclassrooms.projet3.excercice1.constants.ApiConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration de la sécurité de l'application.
 * Définit les règles d'authentification, d'autorisation et les filtres de
 * sécurité.
 * 
 * @author Kévin Renault
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;
    private final LoggingFilter loggingFilter;

    /**
     * Configure la chaîne de filtres de sécurité Spring Security.
     * Définit les routes publiques et privées, et configure JWT.
     * 
     * @param http L'objet HttpSecurity à configurer
     * @return La chaîne de filtres de sécurité configurée
     * @throws Exception En cas d'erreur de configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF désactivé pour API REST stateless avec JWT
                // Protection CSRF inutile car pas de cookies de session
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Routes publiques
                        .requestMatchers(ApiConstants.AUTH_PUBLIC_PATHS).permitAll()
                        .requestMatchers(ApiConstants.UPLOADS_PUBLIC_PATHS).permitAll()
                        .requestMatchers(ApiConstants.SWAGGER_PATHS, ApiConstants.API_DOCS_PATHS,
                                ApiConstants.SWAGGER_HTML_PATH)
                        .permitAll()
                        // Toutes les autres routes nécessitent une authentification
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(
                                (request, response, authException) -> response.sendError(401, "Unauthorized")))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(loggingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Bean pour l'encodage des mots de passe avec BCrypt.
     * 
     * @return Un encodeur de mot de passe BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean pour le fournisseur d'authentification.
     * Configure l'authentification avec UserDetailsService et BCrypt.
     * 
     * @return Le fournisseur d'authentification configuré
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Bean pour le gestionnaire d'authentification.
     * 
     * @param config La configuration d'authentification Spring
     * @return Le gestionnaire d'authentification
     * @throws Exception En cas d'erreur de récupération du manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
