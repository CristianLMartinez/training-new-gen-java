package com.globant.trainingnewgen.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime deliveryDate;

    @PrePersist
    private void prePersist() {
        if (uuid == null)
            uuid = UUID.randomUUID();

        if(status == null)
            status = OrderStatus.ON_THE_WAY;

        if (creationDateTime == null)
            creationDateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", creationDateTime=" + creationDateTime +
                ", quantity=" + quantity +
                ", extraInformation='" + extraInformation + '\'' +
                ", subTotal=" + subTotal +
                ", tax=" + tax +
                ", grandTotal=" + grandTotal +
                ", status=" + status +
                ", deliveryDate=" + deliveryDate +
                '}';
    }
}