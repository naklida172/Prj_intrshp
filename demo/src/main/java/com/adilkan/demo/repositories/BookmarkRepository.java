package com.adilkan.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adilkan.demo.entities.Bookmark;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

}
