package com.ecom_project.shopify.service;

import com.ecom_project.shopify.model.Cart;
import com.ecom_project.shopify.model.Product;
import com.ecom_project.shopify.repository.CartRepo;
import com.ecom_project.shopify.repository.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartService {

    @Autowired
    CartRepo cartRepo;

    @Autowired
    ProductService productService;

    public Cart getCartByCustomerId(UUID custId) {
        return cartRepo.findByCustomerId(custId);
    }

    public void createCartForCustomer(Cart cart){
        cartRepo.save(cart);
    }


    @Transactional
    public Cart addToCart(String name, UUID custId){

        Cart cart = getCartByCustomerId(custId);
        Product product = productService.getProdByName(name);

        if (product.getStock() <= 0) {
            throw new RuntimeException("Out of stock");
        }
        cart.getProductList().add(product);
        product.setStock(product.getStock() - 1);
        return cart;
    }

    @Transactional
    public Cart removeFromCart(String name, UUID custId){
        Cart cart = getCartByCustomerId(custId);
        Product product = productService.getProdByName(name);
        cart.getProductList().remove(product);
        product.setStock(product.getStock() + 1);

        return cart;
    }
}
