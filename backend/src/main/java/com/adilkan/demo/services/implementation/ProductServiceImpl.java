package com.adilkan.demo.services.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adilkan.demo.dtos.ProductDTO;
import com.adilkan.demo.entities.Product;
import com.adilkan.demo.exceptions.ResourceNotFoundException;
import com.adilkan.demo.mappers.ProductMapper;
import com.adilkan.demo.repositories.ProductRepository;
import com.adilkan.demo.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) {
        if (productDTO == null) {
            throw new IllegalArgumentException("ProductDTO cannot be null.");
        }

        Product product = ProductMapper.toEntity(productDTO);
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null.");
        }

        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found.");
        }
        return products;
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) {
        if (id == null || productDTO == null) {
            throw new IllegalArgumentException("Product ID and ProductDTO cannot be null.");
        }

        Product existingProduct = getProductById(id);


        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setRating(productDTO.getRating());
        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Product ID cannot be null.");
        }

        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }

        productRepository.deleteById(id);
    }
}
