package com.company.auth.service;

import com.company.auth.dto.ProductRequestDTO;
import com.company.auth.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO);
    void deleteProduct(Long id);
    ProductResponseDTO getProduct(Long id);
    List<ProductResponseDTO> getAllProducts();
}
