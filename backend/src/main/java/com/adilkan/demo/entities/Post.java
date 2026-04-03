package com.adilkan.demo.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
public class Post {

    //TODO: Remove nullable when the project is ready for production. It is only for testing purposes.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Ownership> ownership;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Product> product;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Column(nullable = true)
    private String description;
    
    @Column(nullable = true)
    private List<String> imageURL;
}
