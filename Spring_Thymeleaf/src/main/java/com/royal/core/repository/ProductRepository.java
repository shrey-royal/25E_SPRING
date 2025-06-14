package com.royal.core.repository;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.royal.core.entity.Product;

@Repository
public class ProductRepository {
	
	private final Map<Integer, Product> db = new HashMap<>();
	private int currentId = 1;
	
	public List<Product> findAll() {
		return new ArrayList<>(db.values());
	}
	
	public Product findById(int id) {
		return db.get(id);
	}
	
	public void save(Product product) {
		if (product.getId() == 0) {
			product.setId(currentId++);
		}
		db.put(product.getId(), product);
	}
	
	public void delete(int id) {
		db.remove(id);
	}
}
