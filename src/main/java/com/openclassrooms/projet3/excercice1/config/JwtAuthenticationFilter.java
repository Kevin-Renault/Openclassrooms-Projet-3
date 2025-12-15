package com.openclassrooms.projet3.excercice1.config;

import com.openclassrooms.projet3.excercice1.constants.SecurityConstants;
import com.openclassrooms.projet3.excercice1.service.CustomUserDetailsService;
import com.openclassrooms.projet3.excercice1.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtre d'authentification JWT pour Spring Security.
 * Intercepte chaque requête HTTP pour valider le token JWT et authentifier
 * l'utilisateur.
 * 
 * @author Kévin Renault
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    /**
     * Méthode principale du filtre exécutée pour chaque requête.
     * Extrait et valide le token JWT, puis authentifie l'utilisateur si valide.
     * 
     * @param request     La requête HTTP
     * @param response    La réponse HTTP
     * @param filterChain La chaîne de filtres à continuer
     * @throws ServletException En cas d'erreur servlet
     * @throws IOException      En cas d'erreur d'I/O
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
        final String jwt;
        final String userEmail;

        // Si pas de token Bearer, continuer la chaîne de filtres
        if (authHeader == null || !authHeader.startsWith(SecurityConstants.BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraire le token
        jwt = authHeader.substring(SecurityConstants.BEARER_PREFIX_LENGTH);
        userEmail = jwtService.extractUsername(jwt);

        // Si l'email est présent et qu'il n'y a pas déjà d'authentification
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Valider le token
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
