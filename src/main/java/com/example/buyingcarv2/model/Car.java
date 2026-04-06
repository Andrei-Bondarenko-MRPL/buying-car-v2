package com.example.buyingcarv2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "car")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Car extends BaseEntity {

    @Column(name = "model")
    private String model;

    @Column(name = "color")
    private String color;

    @Column(name = "price")
    private Long price;
}
