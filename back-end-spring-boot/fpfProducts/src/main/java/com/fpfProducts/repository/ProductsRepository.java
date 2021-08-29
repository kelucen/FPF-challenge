package com.fpfProducts.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpfProducts.model.Product;

public interface ProductsRepository extends JpaRepository<Product, Long> {
	List<Product> findByCategory(String category);
	
	List<Product> findByName(String name);
}
