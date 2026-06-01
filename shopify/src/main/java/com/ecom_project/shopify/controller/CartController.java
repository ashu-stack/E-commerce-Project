package com.ecom_project.shopify.controller;

import com.ecom_project.shopify.dto.CartDTO;
import com.ecom_project.shopify.dto.CustomerDTO;
import com.ecom_project.shopify.dto.Mapper;
import com.ecom_project.shopify.model.Cart;
import com.ecom_project.shopify.model.Customer;
import com.ecom_project.shopify.model.Product;
import com.ecom_project.shopify.repository.CustomerRepo;
import com.ecom_project.shopify.service.CartService;
import com.ecom_project.shopify.service.CustomerService;
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

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private Mapper mapper;

    private Cart cart;

    @GetMapping("user/cart/showItems/{custId}")
    public ResponseEntity<CartDTO> showCart(@PathVariable UUID custId){
        cart = cartService.getCartByCustomerId(custId);

        Customer customer = customerRepo.findById(custId).orElse(null);
        CustomerDTO customerDTO = mapper.customerDTO(customer);
//        cart.setCustomer(customer);

        CartDTO cartDTO = mapper.cartDTO(cart, customerDTO);

        if(cart != null){
            return new ResponseEntity<>(cartDTO, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    // add item
    @PostMapping("user/cart/addItem/{name}/{custId}")
    public ResponseEntity<Cart> addItem(@PathVariable String name, @PathVariable UUID custId, @RequestParam int quantity){
        cart = cartService.addToCart(name,custId, quantity);
        if(cart != null){
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // remove item
    @DeleteMapping("user/cart/removeItem")
    public ResponseEntity<Cart> removeItem(@RequestParam String name, @RequestParam UUID custId){
        cart = cartService.removeFromCart(name,custId);
        if(cart != null){
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
