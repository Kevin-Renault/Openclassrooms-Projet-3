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
 * Entité représentant une location immobilière dans l'application ChâTop.
 * Une location appartient à un propriétaire (User) et peut recevoir des
 * messages.
 * 
 * @author Kévin Renault
 */
@Entity
@Table(name = "rentals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rental {

    /**
     * Identifiant unique de la location (clé primaire).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom ou titre de la location.
     */
    @Column(nullable = false, length = 255)
    private String name;

    /**
     * Surface de la location en mètres carrés.
     */
    @Column(nullable = false)
    private Integer surface;

    /**
     * Prix de la location (par jour/mois selon configuration).
     */
    @Column(nullable = false)
    private Integer price;

    /**
     * URL de l'image principale de la location.
     */
    @Column(length = 255)
    private String picture;

    /**
     * Description détaillée de la location.
     */
    @Column(length = 2000)
    private String description;

    /**
     * Propriétaire de la location.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    /**
     * Date et heure de création de l'annonce.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Date et heure de la dernière mise à jour de l'annonce.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Liste des messages reçus concernant cette location.
     */
    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages;
}
