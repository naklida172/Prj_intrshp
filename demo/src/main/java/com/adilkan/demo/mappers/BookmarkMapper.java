package com.adilkan.demo.mappers;

import com.adilkan.demo.dtos.BookmarkDTO;
import com.adilkan.demo.entities.Bookmark;
import com.adilkan.demo.entities.Product;
import com.adilkan.demo.entities.User;

public class BookmarkMapper {
    public static BookmarkDTO toDTO(Bookmark Bookmark) {
        if (Bookmark == null) return null;
        return BookmarkDTO.builder()
                .id(Bookmark.getId())
                .BookmarkDate(Bookmark.getSaveDate())
                .productId(Bookmark.getProduct() != null ? Bookmark.getProduct().getId() : null)
                .userId(Bookmark.getUser() != null ? Bookmark.getUser().getId() : null)
                .build();
    }

    public static Bookmark toEntity(BookmarkDTO BookmarkDTO) {
        if (BookmarkDTO == null) return null;
    
        Product product = BookmarkDTO.getProductId() != null ? Product.builder().id(BookmarkDTO.getProductId()).build() : null;
        User user = BookmarkDTO.getUserId() != null ? User.builder().id(BookmarkDTO.getUserId()).build() : null;
    
        return Bookmark.builder()
                .BookmarkDate(BookmarkDTO.getSaveDate())
                .product(product)
                .user(user)
                .build();
    }
    
    
}
