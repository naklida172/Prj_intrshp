package com.adilkan.demo.services;

import java.util.List;

import com.adilkan.demo.dtos.ProductDTO;
import com.adilkan.demo.entities.Product;

public interface ProductService {
    Product createProduct(ProductDTO productDTO);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    Product updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
}
