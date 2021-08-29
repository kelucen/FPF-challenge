package com.fpfProducts.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpfProducts.model.Product;
import com.fpfProducts.repository.ProductsRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")

public class ProductsController {

	@Autowired
	ProductsRepository productsRepository;

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts(@RequestParam(required = false) String name) {
		try {
			List<Product> products = new ArrayList<Product>();

			if (name == null)
				productsRepository.findAll().forEach(products::add);
			else
				productsRepository.findByName(name).forEach(products::add);

			if (products.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(products, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
		Optional<Product> productData = productsRepository.findById(id);

		if (productData.isPresent()) {
			return new ResponseEntity<>(productData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/product")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		try {
			Product _product = productsRepository
					.save(new Product(product.getName(), product.getDescription(), product.getDate(), product.getPrice(), product.getCategory()));
			return new ResponseEntity<>(_product, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/product/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
		Optional<Product> productData = productsRepository.findById(id);

		if (productData.isPresent()) {
			Product _product = productData.get();
			_product.setName(product.getName());
			_product.setDescription(product.getDescription());
			_product.setCategory(product.getCategory());
			_product.setPrice(product.getPrice());
			_product.setDate(product.getDate());
			return new ResponseEntity<>(productsRepository.save(_product), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
		try {
			productsRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/product/category")
	public ResponseEntity<List<Product>> findByCategory(@RequestParam(required = true) String category) {
		try {
			List<Product> produts = productsRepository.findByCategory(category);

			if (produts.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(produts, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}