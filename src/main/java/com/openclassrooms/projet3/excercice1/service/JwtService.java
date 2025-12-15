package com.openclassrooms.projet3.excercice1.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service de gestion des tokens JWT (JSON Web Token).
 * Gère la génération, la validation et l'extraction d'informations des tokens.
 * 
 * @author Kévin Renault
 */
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    /**
     * Extrait le nom d'utilisateur (email) du token JWT.
     * 
     * @param token Le token JWT
     * @return Le nom d'utilisateur (email) contenu dans le token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrait une claim spécifique du token JWT.
     * 
     * @param <T>            Le type de la claim
     * @param token          Le token JWT
     * @param claimsResolver La fonction pour extraire la claim des Claims
     * @return La valeur de la claim extraite
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Génère un token JWT pour un utilisateur.
     * 
     * @param userDetails Les détails de l'utilisateur
     * @return Le token JWT généré
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Génère un token JWT avec des claims supplémentaires.
     * 
     * @param extraClaims Claims supplémentaires à ajouter au token
     * @param userDetails Les détails de l'utilisateur
     * @return Le token JWT généré
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Construit un token JWT avec les paramètres spécifiés.
     * 
     * @param extraClaims Claims supplémentaires à inclure
     * @param userDetails Les détails de l'utilisateur
     * @param expiration  Durée de validité du token en millisecondes
     * @return Le token JWT construit
     */
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Vérifie si un token JWT est valide pour un utilisateur.
     * 
     * @param token       Le token JWT à valider
     * @param userDetails Les détails de l'utilisateur
     * @return true si le token est valide, false sinon
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Vérifie si un token JWT est expiré.
     * 
     * @param token Le token JWT
     * @return true si le token est expiré, false sinon
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrait la date d'expiration du token JWT.
     * 
     * @param token Le token JWT
     * @return La date d'expiration
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrait toutes les claims du token JWT.
     * 
     * @param token Le token JWT
     * @return Toutes les claims contenues dans le token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Récupère la clé de signature pour les tokens JWT.
     * 
     * @return La clé de signature
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
