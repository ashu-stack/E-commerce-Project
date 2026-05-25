package com.ecom_project.shopify.service;

import com.ecom_project.shopify.dto.Mapper;
import com.ecom_project.shopify.dto.ProductDTO;
import com.ecom_project.shopify.model.Product;
import com.ecom_project.shopify.repository.ProductRepo;

import java.util.ArrayList;
import java.util.List;

import com.ecom_project.shopify.util.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    Mapper mapper;

//    @Autowired
//    private RedisService redisService;

    @Cacheable(cacheNames = "products", key = "#category")
    public List<ProductDTO> getAllProd(Category category) {

        List<Product> productList =  productRepo.findByCategory(category);
        List<ProductDTO> dtos = new ArrayList<>();
        for(Product product : productList){
            ProductDTO dto = mapper.productDTO(product);
            dtos.add(dto);
        }
        return dtos;
    }


    public void addNewProd(Product product) {
        productRepo.save(product);
    }

    @Cacheable(cacheNames = "products", key = "#id")
    public Product getProdByName(String name) {
        return productRepo.findByName(name).orElse(null);
    }



    @CacheEvict(cacheNames = "products", key = "#id")
    public void deleteProdById(int id) {
        productRepo.deleteById(id);
    }
}
