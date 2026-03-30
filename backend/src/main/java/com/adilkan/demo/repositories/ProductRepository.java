package com.adilkan.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adilkan.demo.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
