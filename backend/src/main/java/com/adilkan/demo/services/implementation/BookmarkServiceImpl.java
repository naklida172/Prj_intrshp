package com.adilkan.demo.services.implementation;

import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adilkan.demo.dtos.BookmarkDTO;
import com.adilkan.demo.entities.Bookmark;
import com.adilkan.demo.exceptions.ResourceNotFoundException;
import com.adilkan.demo.mappers.BookmarkMapper;
import com.adilkan.demo.repositories.BookmarkRepository;
import com.adilkan.demo.services.BookmarkService;

@Service
public class BookmarkServiceImpl implements BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Override
    public Bookmark createBookmark(BookmarkDTO bookmarkDTO) {
        if (bookmarkDTO == null) {
            throw new IllegalArgumentException("BookmarkDTO cannot be null.");
        }

        Bookmark bookmark = BookmarkMapper.toEntity(bookmarkDTO);
        return bookmarkRepository.save(bookmark);
    }

    @Override
    public Bookmark getBookmarkById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Bookmark ID cannot be null.");
        }

        return bookmarkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bookmark not found with id: " + id));
    }

    @Override
    public List<Bookmark> getAllBookmarks() {
        List<Bookmark> bookmarks = bookmarkRepository.findAll();
        if (bookmarks.isEmpty()) {
            throw new ResourceNotFoundException("No bookmarks found.");
        }
        return bookmarks;
    }

    @Override
    public Bookmark updateBookmark(Long id, BookmarkDTO bookmarkDTO) {
        if (id == null || bookmarkDTO == null) {
            throw new IllegalArgumentException("Bookmark ID and BookmarkDTO cannot be null.");
        }

        Bookmark existingBookmark = getBookmarkById(id);
        existingBookmark.setSaveDate(bookmarkDTO.getSaveDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime());

        return bookmarkRepository.save(existingBookmark);
    }

    @Override
    public void deleteBookmark(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Bookmark ID cannot be null.");
        }

        if (!bookmarkRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bookmark not found with id: " + id);
        }

        bookmarkRepository.deleteById(id);
    }
}
