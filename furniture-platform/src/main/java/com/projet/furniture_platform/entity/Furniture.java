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

    private String name;

    // TYPE
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    // DESCRIPTION
    @Column(columnDefinition = "TEXT")
    private String description;

    private BigDecimal height;
    private BigDecimal width;

    // COLOR (optionnel)
    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    // MATERIAL (optionnel)
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private Status status;

    // ORDER — nullable car il n’existe pas au dépôt
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // ADDRESS — simple clé étrangère
    @Column(name = "address_id")
    private Integer addressId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "furniture", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference   // ← obligatoire pour casser la récursion JSON
    private List<Picture> pictures;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) this.status = Status.AVAILABLE;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum Status {
        DRAFT,
        PENDING,
        VALIDATED,
        AVAILABLE,
        RESERVED,
        SOLD,
        DELETED,
        REJECTED
    }
}
