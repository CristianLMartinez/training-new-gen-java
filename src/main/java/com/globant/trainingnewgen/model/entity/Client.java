package com.globant.trainingnewgen.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients", indexes = {
        @Index(name = "index_document", columnList = "document")
})
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String document;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 11)
    private String phone;

    @Column(nullable = false, length = 500)
    private String deliveryAddress;

    private boolean isDeleted = Boolean.FALSE;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Order> orders;



    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", document='" + document + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }

}