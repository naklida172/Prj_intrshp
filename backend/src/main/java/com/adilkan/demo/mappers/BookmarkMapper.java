package com.adilkan.demo.mappers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.adilkan.demo.dtos.BookmarkDTO;
import com.adilkan.demo.entities.Bookmark;
import com.adilkan.demo.entities.Product;
import com.adilkan.demo.entities.User;

public class BookmarkMapper {
    public static BookmarkDTO toDTO(Bookmark bookmark) {
        if (bookmark == null) return null;
        Date saveDate = bookmark.getSaveDate() != null ?
                Date.from(bookmark.getSaveDate().atZone(ZoneId.systemDefault()).toInstant()) : null;

        return BookmarkDTO.builder()
                .id(bookmark.getId())
                .saveDate(saveDate)
                .productId(bookmark.getProduct() != null ? bookmark.getProduct().getId() : null)
                .userId(bookmark.getUser() != null ? bookmark.getUser().getId() : null)
                .build();
    }

    public static Bookmark toEntity(BookmarkDTO bookmarkDTO) {
        if (bookmarkDTO == null) return null;

        Product product = bookmarkDTO.getProductId() != null ? Product.builder().id(bookmarkDTO.getProductId()).build() : null;
        User user = bookmarkDTO.getUserId() != null ? User.builder().id(bookmarkDTO.getUserId()).build() : null;
        LocalDateTime orderDate = bookmarkDTO.getSaveDate() != null ?
                LocalDateTime.ofInstant(bookmarkDTO.getSaveDate().toInstant(), ZoneId.systemDefault()) : null;

        return Bookmark.builder()
                .saveDate(orderDate)
                .product(product)
                .user(user)
                .build();
    }
}

