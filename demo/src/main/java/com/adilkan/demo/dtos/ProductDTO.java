package kg.alatoo.midterm.dtos;

import java.util.List;

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
    private short rating;
    private Long sellerId;   // FK to Seller
    private List<Long> tagIds; // List of FK to Tags
}

