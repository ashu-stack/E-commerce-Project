package com.ecom_project.shopify.model;


import com.ecom_project.shopify.util.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "prod_seq"
    )
    @SequenceGenerator(name = "prod_seq",
    allocationSize = 2)
    private Integer id;
    @Column(
            name = "product_name",
            unique = true,
            nullable = false
    )
    private String name;
    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private boolean available;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(length = 100)
    private String description;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

}
