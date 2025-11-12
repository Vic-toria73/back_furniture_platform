package com.projet.furniture_platform.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Short latitude;

    private Short longitude;

    @Column(length = 255)
    private String name;

    @Column(name = "city_id", length = 255)
    private String cityId;

    @Column(name = "api_id", length = 255)
    private String apiId;


}