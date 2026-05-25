package com.ecom_project.shopify.repository;

import com.ecom_project.shopify.model.Product;
import com.ecom_project.shopify.util.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {
        Optional<Product> findByName(String name);

        List<Product> findByCategory(Category category);
}
