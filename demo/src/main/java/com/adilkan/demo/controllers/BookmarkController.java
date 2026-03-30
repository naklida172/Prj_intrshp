package com.adilkan.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.adilkan.demo.dtos.BookmarkDTO;
import com.adilkan.demo.entities.Bookmark;
import com.adilkan.demo.services.BookmarkService;

@EnableWebMvc
@RestController
@RequestMapping("/api/bookmarks")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @PostMapping
    public Bookmark createBookmark(@RequestBody BookmarkDTO bookmarkDTO) {
        return bookmarkService.createBookmark(bookmarkDTO);
    }

    @GetMapping("/{id}")
    public Bookmark getBookmarkById(@PathVariable Long id) {
        return bookmarkService.getBookmarkById(id);
    }

    @GetMapping
    public List<Bookmark> getAllBookmarks() {
        return bookmarkService.getAllBookmarks();
    }

    @PutMapping("/{id}")
    public Bookmark updateBookmark(@PathVariable Long id, @RequestBody BookmarkDTO bookmarkDTO) {
        return bookmarkService.updateBookmark(id, bookmarkDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBookmark(@PathVariable Long id) {
        bookmarkService.deleteBookmark(id);
    }
}
