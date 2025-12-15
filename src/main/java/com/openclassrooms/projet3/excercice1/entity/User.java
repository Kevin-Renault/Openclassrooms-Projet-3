package com.openclassrooms.projet3.excercice1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entité représentant un utilisateur de l'application ChâTop.
 * Un utilisateur peut être propriétaire de locations et envoyer des messages.
 * 
 * @author Kévin Renault
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Identifiant unique de l'utilisateur (clé primaire).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom complet de l'utilisateur.
     */
    @Column(nullable = false, length = 255)
    private String name;

    /**
     * Adresse email de l'utilisateur (unique dans le système).
     */
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    /**
     * Mot de passe hashé de l'utilisateur.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Date et heure de création du compte utilisateur.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Date et heure de la dernière mise à jour du compte.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Liste des locations dont cet utilisateur est propriétaire.
     */
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rental> rentals;

    /**
     * Liste des messages envoyés par cet utilisateur.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages;
}
