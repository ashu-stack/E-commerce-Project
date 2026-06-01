package com.ecom_project.shopify.dto;

import com.ecom_project.shopify.model.Cart;
import com.ecom_project.shopify.model.Customer;
import com.ecom_project.shopify.model.Product;
import com.ecom_project.shopify.util.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapper {

    public CustomerDTO customerDTO(Customer customer){
        String name = customer.getName();
        String lastName = customer.getLastName();
        String email = customer.getEmail();

        return new CustomerDTO(name, lastName,email);
    }

    public ProductDTO productDTO(Product product){
        String name = product.getName();
        Integer price = product.getPrice();
        boolean available = product.isAvailable();
        String description = product.getDescription();
        Category category = product.getCategory();

        return new ProductDTO(name,price,available,category, description);

    }

    public CartDTO cartDTO(Cart cart, CustomerDTO customerDTO){
         List<Product> productList = cart.getProductList();
        String modeOfPayment = cart.getModeOfPayment();

        return new CartDTO(productList,modeOfPayment,customerDTO);
    }


}
