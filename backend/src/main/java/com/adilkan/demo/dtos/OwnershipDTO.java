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
public class OwnershipDTO {
    private Long id;
    private List<Long> userIds; // List of FK to Users
    private List<Long> productIds; // List of FK to Products
}
