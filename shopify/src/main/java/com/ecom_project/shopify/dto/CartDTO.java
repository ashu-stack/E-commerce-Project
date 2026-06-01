package com.ecom_project.shopify.dto;

import com.ecom_project.shopify.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor

public class CartDTO {
    private List<Product> productList;
    private String modeOfPayment;
    private CustomerDTO customerDTO;
}
