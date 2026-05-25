package com.ecom_project.shopify.controller;

import com.ecom_project.shopify.model.Cart;
import com.ecom_project.shopify.model.Product;
import com.ecom_project.shopify.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    private Cart cart;

    @GetMapping("user/cart/showItems/{custId}")
    public ResponseEntity<Cart> showCart(@PathVariable UUID custId){
        cart = cartService.getCartByCustomerId(custId);
        if(cart != null){
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    // add item
    @PostMapping("user/cart/addItem/{productId}/{custId}")
    public ResponseEntity<Cart> addItem(@PathVariable String name, @PathVariable UUID custId){
        cart = cartService.addToCart(name,custId);
        if(cart != null){
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    // remove item
    @DeleteMapping("user/cart/removeItem/{productId}/{custId}")
    public ResponseEntity<Cart> removeItem(@PathVariable String name, @PathVariable UUID custId){
        cart = cartService.removeFromCart(name,custId);
        if(cart != null){
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
