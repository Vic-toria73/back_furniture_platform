package com.projet.furniture_platform.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table (name = "furniture")
@Data
public class Furniture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "type_id", nullable = false)
    private Integer typeId;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "height", nullable = false, precision = 10, scale = 2)
    private BigDecimal height;

    @Column(name = "width", nullable = false, precision = 10, scale = 2)
    private BigDecimal width;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "enum('to validate','validated','rejected')")
    private FurnitureStatus status;

    @Column(name = "order_id", nullable = false)
    private Integer orderId;

    @Column(name = "address_id", nullable = false)
    private Integer addressId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    private Timestamp updatedAt;

    // Définition de l'énumération pour le statut
    public enum FurnitureStatus {
        TO_VALIDATE("to validate"),
        VALIDATED("validated"),
        REJECTED("rejected");

        private final String value;

        FurnitureStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    // Relations avec les autres entités (optionnel, si tu veux utiliser des objets plutôt que des IDs)
    // Exemple pour la relation avec User :
     /*@ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "user_id", insertable = false, updatable = false)
     private User user;*/
}
