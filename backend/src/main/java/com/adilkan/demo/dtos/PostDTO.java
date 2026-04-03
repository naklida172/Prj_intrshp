package com.adilkan.demo.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private List<Long> productIds; // List of FK to Products
    private String description;
    private List<String> imageURL; // List of FK to Ownership
}
