package com.ecom_project.shopify.model;

import com.ecom_project.shopify.dto.CustomerDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(nullable = false)
    private UUID customerId;

    @ManyToMany
    private List<Product> productList;
    @Column(nullable = false)
    private String modeOfPayment;

    @OneToOne(mappedBy = "cart")
    private Customer customer;

    private CustomerDTO customerDTO;
}
