package com.projet.furniture_platform.DTO;

import com.projet.furniture_platform.entity.Furniture;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FurnitureDTO {

    @NotNull(message = "Le nom est obligatoire")
    private String name;

    @NotNull(message = "Le type est obligatoire")
    private Integer typeId;

    private String description;

    @NotNull(message = "La hauteur est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal height;

    @NotNull(message = "La largeur est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal width;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    // 🚀 Correction ici :
    private Furniture.Status status;

    @NotNull(message = "L'ID de la commande est obligatoire")
    private Integer orderId;

    @NotNull(message = "L'ID de l'adresse est obligatoire")
    private Integer addressId;

    @NotNull(message = "L'ID de l'utilisateur est obligatoire")
    private Integer userId;
}
