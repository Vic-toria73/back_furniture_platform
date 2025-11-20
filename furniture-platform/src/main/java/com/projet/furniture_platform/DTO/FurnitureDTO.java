package com.projet.furniture_platform.DTO;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FurnitureDTO {

    private String name;
    private Integer typeId;

    private String description;

    private BigDecimal height;
    private BigDecimal width;
    private BigDecimal price;

    private Integer colorId;
    private Integer materialId;

    private Integer addressId;  // optionnel
    private Integer userId;     // obligatoire

}

