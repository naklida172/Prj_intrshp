package com.adilkan.demo.mappers;

import com.adilkan.demo.dtos.ProductDTO;
import com.adilkan.demo.entities.Product;

public class ProductMapper {
    public static ProductDTO toDTO(Product product) {
        if (product == null) return null;
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .rating(product.getRating())
                .build();
    }

    public static Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) return null;

        return Product.builder()
                .id(productDTO.getId() != null ? productDTO.getId() : 0)
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .rating(productDTO.getRating())
                .build();
    }
}
