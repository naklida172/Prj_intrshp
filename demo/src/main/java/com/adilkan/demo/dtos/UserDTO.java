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
public class UserDTO {
    private Long id;
    private String name;
    private String description;
    private String username;
    private String password;
    private String role;
    private String email;
    private String phone;
    private List<Long> sellerIds; // List of FK to Sellers
}
