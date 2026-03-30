package com.adilkan.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Short rating;
}

