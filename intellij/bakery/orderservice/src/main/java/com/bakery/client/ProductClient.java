package com.bakery.client;

import com.bakery.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "productservice", url = "http://localhost:9002")
public interface ProductClient {
    @GetMapping("/products/{id}")
    ProductDto getSingleProduct(@PathVariable Long id);
}
