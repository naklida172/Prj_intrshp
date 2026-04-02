package com.adilkan.demo.services;

import java.util.List;

import com.adilkan.demo.dtos.BookmarkDTO;
import com.adilkan.demo.entities.Bookmark;

public interface BookmarkService {
    Bookmark createBookmark(BookmarkDTO bookmarkDTO);
    Bookmark getBookmarkById(Long id);
    List<Bookmark> getAllBookmarks();
    Bookmark updateBookmark(Long id, BookmarkDTO bookmarkDTO);
    void deleteBookmark(Long id);
}
