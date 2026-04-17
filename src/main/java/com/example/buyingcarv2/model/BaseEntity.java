package com.example.buyingcarv2.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;
}
