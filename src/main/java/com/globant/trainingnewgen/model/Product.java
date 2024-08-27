package com.globant.trainingnewgen.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products", indexes = {
        @Index(name = "product_uuid", columnList = "uuid")
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(updatable = false, nullable = false, unique = true)
    private UUID uuid;

    @Column(unique = true, nullable = false, length = 250)
    private String fantasyName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Column(nullable = false, length = 511)
    private String description;

    @DecimalMin(value = "0.01")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean available;

    private boolean isDeleted = Boolean.FALSE;

    /**
     * Pre persist inject the value in the entity before jpa insert the value in the database for first time
     */
    @PrePersist
    private void generateUUID() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }

}
