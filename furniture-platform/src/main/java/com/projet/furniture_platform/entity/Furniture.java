package com.projet.furniture_platform.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "furniture")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Furniture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "type_id", nullable = false)
    private Integer typeId;

    @Column(columnDefinition = "TEXT")
    private String description;

    private BigDecimal height;
    private BigDecimal width;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "address_id")
    private Integer addressId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // ------------------------------------------------------
    // 🔗 RELATION INVERSE : liste de photos du meuble
    // ------------------------------------------------------
    @OneToMany(mappedBy = "furniture", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Picture> pictures;


    // ------------------------------------------------------
    // ⏱ Gestion automatique des timestamps
    // ------------------------------------------------------
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ------------------------------------------------------
    // 🔐 Enum status conforme à ton schéma
    // ------------------------------------------------------
    public enum Status {
        DRAFT,        // Le meuble est créé mais pas encore soumis
        PENDING,      // L'annonce est en attente de validation
        VALIDATED,    // L'équipe a validé l'annonce
        AVAILABLE,    // Visible et disponible à l'achat
        RESERVED,     // Réservé par un client mais pas encore acheté
        SOLD,         // L'objet est vendu
        DELETED       // Supprimé (soft delete)
    }
}
