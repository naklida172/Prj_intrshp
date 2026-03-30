package com.adilkan.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users") 
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(max = 100, message = "Name must not exceed 100 characters")
    @Column(nullable = true)
    private String name;

    @Size(max = 50, message = "Username must not exceed 50 characters")
    @Column(nullable = true)
    private String username;

    @Size(max = 10, message = "Password must not exceed 10 characters")
    @Column(nullable = true)
    private String password;

    @Size(max = 20, message = "Role must not exceed 20 characters")
    @Column(nullable = true)
    private String role;

    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Column(nullable = true)
    private String email;

    @Size(max = 15, message = "Phone number must not exceed 15 characters")
    @Column(nullable = true)
    private String phone;
}
