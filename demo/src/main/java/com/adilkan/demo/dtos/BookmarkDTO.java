package com.adilkan.demo.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BookmarkDTO {
    private Long id;
    private Date saveDate;
    private Long productId; // FK to Product
    private Long userId;    // FK to User
}
