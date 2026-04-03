package com.adilkan.demo.mappers;

import java.util.ArrayList;
import java.util.List;

import com.adilkan.demo.dtos.OwnershipDTO;
import com.adilkan.demo.entities.Ownership;
import com.adilkan.demo.entities.Product;
import com.adilkan.demo.entities.User;

public class OwnershipMapper {
    public static OwnershipDTO toDTO(Ownership ownership) {
        if (ownership == null) return null;
        return OwnershipDTO.builder()
                .id(ownership.getId())
                .userIds(ownership.getUser() != null
                        ? ownership.getUser().stream().map(User::getId).toList()
                        : null)
                .collaboratorIds(ownership.getCollaborator() != null
                        ? ownership.getCollaborator().stream().map(User::getId).toList()
                        : null)
                .productIds(ownership.getProduct() != null ? ownership.getProduct().stream().map(Product::getId).toList()
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

    List<User> collaborators = new ArrayList<>();
    if (OwnershipDTO.getCollaboratorIds() != null) {
        for (Long collaboratorId : OwnershipDTO.getCollaboratorIds()) {
            collaborators.add(User.builder().id(collaboratorId).build());
        }
    }

    return Ownership.builder()
            .user(users)
            .collaborator(collaborators)
            .product(products)
            .build();
    }
}
