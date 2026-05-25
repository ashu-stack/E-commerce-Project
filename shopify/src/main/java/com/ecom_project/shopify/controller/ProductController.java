package com.ecom_project.shopify.controller;


import com.ecom_project.shopify.dto.Mapper;
import com.ecom_project.shopify.dto.ProductDTO;
import com.ecom_project.shopify.model.Customer;
import com.ecom_project.shopify.model.Product;
import com.ecom_project.shopify.service.ProductService;
import com.ecom_project.shopify.util.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    Mapper mapper;

    @RequestMapping("/")
    public String greet(){
        return "Welcome to Shopify";
    }

    @GetMapping("user/product/category/{category}")
    public ResponseEntity<List<ProductDTO>> getAllProducts(@PathVariable Category category){
        List<ProductDTO> productDTOS =   productService.getAllProd(category);
        return new ResponseEntity<>(productDTOS,HttpStatus.OK);
    }

    @GetMapping("user/product")
    public ResponseEntity<ProductDTO> getProductById(@RequestParam String name){
        Product product =  productService.getProdByName(name);
        ProductDTO dto = null;
        if(product != null) {
             dto = mapper.productDTO(product);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("admin/product")
    public ResponseEntity addProduct(@RequestBody Product product){
        productService.addNewProd(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("admin/product/{id}")

    public ResponseEntity deleteProduct(@PathVariable int id){
        productService.deleteProdById(id);
        return new ResponseEntity<>(HttpStatus.GONE);
    }
}
