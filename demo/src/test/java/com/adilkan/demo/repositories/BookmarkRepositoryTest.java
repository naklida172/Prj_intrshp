package com.adilkan.demo.repositories;

import com.adilkan.demo.entities.Bookmark;
import com.adilkan.demo.entities.Product;
import com.adilkan.demo.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookmarkRepositoryTest {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private ProductRepository productRepository; // assuming you have this
    @Autowired
    private UserRepository userRepository;       // assuming you have this

    @Test
    void testSaveAndFindById() {
        // Arrange: create related entities
        Product product = new Product();
        product.setName("Test Product");
        product = productRepository.save(product);

        User user = new User();
        user.setUsername("adilkan");
        user = userRepository.save(user);

        // Create a Bookmark
        Bookmark bookmark = Bookmark.builder()
                .saveDate(LocalDateTime.now())
                .product(product)
                .user(user)
                .build();

        // Act: save it
        Bookmark saved = bookmarkRepository.save(bookmark);

        // Assert: verify it can be retrieved
        Optional<Bookmark> found = bookmarkRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getProduct().getName()).isEqualTo("Test Product");
        assertThat(found.get().getUser().getUsername()).isEqualTo("adilkan");
    }

    @Test
    void testDelete() {
        Product product = new Product();
        product.setName("Delete Product");
        product = productRepository.save(product);

        User user = new User();
        user.setUsername("deleteUser");
        user = userRepository.save(user);

        Bookmark bookmark = Bookmark.builder()
                .saveDate(LocalDateTime.now())
                .product(product)
                .user(user)
                .build();

        Bookmark saved = bookmarkRepository.save(bookmark);

        bookmarkRepository.delete(saved);

        Optional<Bookmark> found = bookmarkRepository.findById(saved.getId());
        assertThat(found).isEmpty();
    }
}
