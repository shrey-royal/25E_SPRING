package com.bakery.service;

import com.bakery.entity.Product;
import com.bakery.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repo;

    public Product save(Product product) {
        return repo.save(product);
    }

    public List<Product> findAll() {
        return repo.findAll();
    }

    public Product findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found!"));
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
