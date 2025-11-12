package com.projet.furniture_platform.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import com.projet.furniture_platform.entity.Furniture;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FurnitureDTO {

    private Integer id;

    @NotBlank(message = "Le nom est obligatoire")
    private String name;

    @NotNull(message = "Le type est obligatoire")
    private Integer typeId;

    @NotBlank(message = "La description est obligatoire")
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

    private Furniture.FurnitureStatus status;

    @NotNull(message = "L'ID de la commande est obligatoire")
    private Integer orderId;

    @NotNull(message = "L'ID de l'adresse est obligatoire")
    private Integer addressId;

    @NotNull(message = "L'ID de l'utilisateur est obligatoire")
    private Integer userId;
}
