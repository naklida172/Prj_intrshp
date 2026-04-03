package com.adilkan.demo.mappers;

import java.util.ArrayList;
import java.util.List;

import com.adilkan.demo.dtos.PostDTO;
import com.adilkan.demo.entities.Post;
import com.adilkan.demo.entities.Product;


public class PostMapper {
    public static PostDTO toDTO(Post post) {
        if (post == null) return null;

        return PostDTO.builder()
                .id(post.getId())
                .productIds(post.getProduct() != null
                        ? post.getProduct().stream().map(Product::getId).toList()
                        : null)
                .description(post.getDescription())
                .imageURL(post.getImageURL())
                .build();
    }

    public static Post toEntity(PostDTO postDTO) {
        if (postDTO == null) return null;

        List<Product> products = new ArrayList<>();
        if (postDTO.getProductIds() != null) {
            for (Long productId : postDTO.getProductIds()) {
                products.add(Product.builder().id(productId).build());
            }
        }

        return Post.builder()
                .id(postDTO.getId() != null ? postDTO.getId() : 0)
                .product(products)
                .description(postDTO.getDescription())
                .imageURL(postDTO.getImageURL())
                .build();
    }
}
