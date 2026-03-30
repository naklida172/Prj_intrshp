package com.adilkan.demo.mappers;

import java.util.ArrayList;
import java.util.List;

import com.adilkan.demo.dtos.OwnershipDTO;
import com.adilkan.demo.entities.Ownership;
import com.adilkan.demo.entities.Product;
import com.adilkan.demo.entities.User;

public class OwnershipMapper {
    public static OwnershipDTO toDTO(Ownership Ownership) {
        if (Ownership == null) return null;
        return OwnershipDTO.builder()
                .id(Ownership.getId())
                .userIds(Ownership.getUser() != null
                        ? Ownership.getUser().stream().map(User::getId).toList()
                        : null)
                .productIds(Ownership.getProduct() != null ? Ownership.getProduct().stream().map(Product::getId).toList()
                : null)
                .build();
    }

    public static Ownership toEntity(OwnershipDTO OwnershipDTO) {
    if (OwnershipDTO == null) return null;

    List<User> users = new ArrayList<>();
    if (OwnershipDTO.getUserIds() != null) {
        for (Long userId : OwnershipDTO.getUserIds()) {
            users.add(User.builder().id(userId).build());
        }
    }

    List<Product> products = new ArrayList<>();
    if (OwnershipDTO.getProductIds() != null) {
        for (Long productId : OwnershipDTO.getProductIds()) {
            products.add(Product.builder().id(productId).build());
        }
    }

    return Ownership.builder()
            .user(users)
            .product(products)
            .build();
    }
}
