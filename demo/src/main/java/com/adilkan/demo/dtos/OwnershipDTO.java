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
public class SellerDTO {
    private Long id;
    private String name;
    private List<Long> userIds; // List of FK to Users
    private List<Long> productIds;
}
