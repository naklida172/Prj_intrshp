package kg.alatoo.midterm.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private Date orderDate;
    private short quantity;
    private String status;
    private Long productId; // FK to Product
    private Long pointId;   // FK to Point
    private Long userId;    // FK to User
}
