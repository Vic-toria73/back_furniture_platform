package com.projet.furniture_platform.DTO;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FurnitureUpdateRequest {
    private String name;
    private BigDecimal price;
    private String description;
    private Integer typeId;
    private Integer colorId;
    private Integer materialId;
}