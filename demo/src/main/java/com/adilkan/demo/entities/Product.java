package com.adilkan.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(max = 100, message = "Name must not exceed 100 characters")
    @Column(nullable = true)
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private Short rating;
}
