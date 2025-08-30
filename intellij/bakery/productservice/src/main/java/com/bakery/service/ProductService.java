package com.bakery.service;

import com.bakery.entity.Product;
import com.bakery.repository.ProductRepository;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repo;
    private final Cloudinary cloudinary;

    public Product save(Product product, MultipartFile image) throws Exception {
        if(image != null && !image.isEmpty()) {
            Map<?, ?> res = cloudinary.uploader().upload(image.getBytes(), Map.of());
            product.setImageUrl((String) res.get("secure_url"));
        }
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
