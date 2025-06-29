package com.royal.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.royal.core.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	//
}
