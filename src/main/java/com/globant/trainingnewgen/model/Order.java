package com.globant.trainingnewgen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import java.util.UUID;


import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders", indexes = {
        @Index(name = "index_order_uuid", columnList = "uuid")
})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Column(nullable = false)
    private UUID uuid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "uuid", nullable = false)
    private Product product;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDateTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_document", referencedColumnName = "document", nullable = false)
    private Client client;

    @Min(1)
    @Max(99)
    @Column(nullable = false)
    private int quantity;

    @Size(max = 511)
    @Column(nullable = false, length = 511)
    private String extraInformation;

    @DecimalMin(value = "0.00")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subTotal;

    @DecimalMin(value = "0.00")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tax;

    @DecimalMin(value = "0.00")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal grandTotal;

    @Column(nullable = false)
    private boolean delivered = false;

    private LocalDateTime deliveryDate;

    @PrePersist
    private void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        if (creationDateTime == null) {
            creationDateTime = LocalDateTime.now();
        }
    }


}