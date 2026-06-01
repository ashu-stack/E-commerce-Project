package com.ecom_project.shopify.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,
    length = 50)
    private String name;

    @Column(length = 50)
    private String lastName;

    @Column(nullable = false,
    unique = true)
    private String email;
    private String password;

    private String providerId;


    @OneToMany(mappedBy = "customer",cascade = CascadeType.REMOVE)
    private List<Orders> ordersList = new ArrayList<>();


    @OneToMany(mappedBy = "customer",cascade = CascadeType.REMOVE)
    private List<Payment> payments = new ArrayList<>();

    @OneToOne
    private Cart cart;


}
