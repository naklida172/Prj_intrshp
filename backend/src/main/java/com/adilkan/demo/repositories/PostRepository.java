package com.adilkan.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adilkan.demo.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
