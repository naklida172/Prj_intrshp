package com.adilkan.demo.bootstrap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.adilkan.demo.entities.AuthToken;
import com.adilkan.demo.entities.Bookmark;
import com.adilkan.demo.entities.Ownership;
import com.adilkan.demo.entities.Post;
import com.adilkan.demo.entities.Product;
import com.adilkan.demo.entities.User;
import com.adilkan.demo.repositories.AuthTokenRepository;
import com.adilkan.demo.repositories.BookmarkRepository;
import com.adilkan.demo.repositories.OwnershipRepository;
import com.adilkan.demo.repositories.PostRepository;
import com.adilkan.demo.repositories.ProductRepository;
import com.adilkan.demo.repositories.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Profile("dev")
public class InitData {

    @Autowired private UserRepository userRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private OwnershipRepository ownershipRepository;
    @Autowired private BookmarkRepository bookmarkRepository;
    @Autowired private AuthTokenRepository authTokenRepository;
    @Autowired private PostRepository postRepository;

    private final Map<Long, User> users = new HashMap<>();
    private final Map<Long, Product> products = new HashMap<>();
    private final Map<Long, Ownership> ownerships = new HashMap<>();
    private final Map<Long, Bookmark> bookmarks = new HashMap<>();
    private final Map<Long, AuthToken> authTokens = new HashMap<>();
    private final Map<Long, Post> posts = new HashMap<>();

    private Long currentId = null;
    private final List<DeferredRelationship> deferredRelationships = new ArrayList<>();

    @PostConstruct
    @Transactional
    public void init() {
        log.info("Loading data from InitData.csv");
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/adilkan/demo/bootstrap/InitData.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 3) continue;
                processLine(values[0], values[1], values[2]);
            }
            processDeferredRelationships();
        } catch (IOException e) {
            log.error("Error reading CSV file", e);
        }
        log.info("Data initialization complete.");
    }

    private void processLine(String entity, String type, String value) {
        switch (entity) {
            case "User": processUser(type, value); break;
            case "Product": processProduct(type, value); break;
            case "Ownership": processOwnership(type, value); break;
            case "Bookmark": processBookmark(type, value); break;
            case "AuthToken": processAuthToken(type, value); break;
            case "Post": processPost(type, value); break;
            default: log.warn("Unknown entity: " + entity);
        }
    }

    // ---------------- USER ----------------
    private void processUser(String type, String value) {
        switch (type) {
            case "id":
                currentId = Long.parseLong(value);
                users.put(currentId, User.builder().build()); // no .id()
                break;
            case "name": users.get(currentId).setName(value); break;
            case "username": users.get(currentId).setUsername(value); break;
            case "password": users.get(currentId).setPassword(value); break;
            case "role": users.get(currentId).setRole(value); break;
            case "email": users.get(currentId).setEmail(value); break;
            case "phone": users.get(currentId).setPhone(value); break;
        }
        if (users.get(currentId) != null) {
            users.put(currentId, userRepository.save(users.get(currentId)));
        }
    }

    // ---------------- PRODUCT ----------------
    private void processProduct(String type, String value) {
        switch (type) {
            case "id":
                currentId = Long.parseLong(value);
                products.put(currentId, Product.builder().build()); // no .id()
                break;
            case "name": products.get(currentId).setName(value); break;
            case "description": products.get(currentId).setDescription(value); break;
            case "rating": products.get(currentId).setRating(Short.parseShort(value)); break;
        }
        if (products.get(currentId) != null) {
            products.put(currentId, productRepository.save(products.get(currentId)));
        }
    }

    // ---------------- OWNERSHIP ----------------
    private void processOwnership(String type, String value) {
        switch (type) {
            case "id":
                currentId = Long.parseLong(value);
                ownerships.put(currentId, Ownership.builder()
                        .user(new ArrayList<>())
                        .product(new ArrayList<>())
                        .build()); // no .id()
                break;
            case "user":
                List<Long> userIds = Arrays.stream(value.split(";")).map(Long::parseLong).toList();
                deferredRelationships.add(new DeferredRelationship(currentId, userIds, "Ownership", "User", "user"));
                break;
            case "collaborator":
                deferredRelationships.add(new DeferredRelationship(currentId, List.of(Long.parseLong(value)), "Ownership", "User", "collaborator"));
                break;
            case "product":
                List<Long> productIds = Arrays.stream(value.split(";")).map(Long::parseLong).toList();
                deferredRelationships.add(new DeferredRelationship(currentId, productIds, "Ownership", "Product", "product"));
                break;
        }
        if (ownerships.get(currentId) != null) {
            ownerships.put(currentId, ownershipRepository.save(ownerships.get(currentId)));
        }
    }

    // ---------------- BOOKMARK ----------------
    private void processBookmark(String type, String value) {
        switch (type) {
            case "id":
                currentId = Long.parseLong(value);
                bookmarks.put(currentId, Bookmark.builder().build()); // no .id()
                break;
            case "saveDate": bookmarks.get(currentId).setSaveDate(LocalDateTime.parse(value)); break;
            case "product":
                deferredRelationships.add(new DeferredRelationship(currentId, List.of(Long.parseLong(value)), "Bookmark", "Product", "product"));
                break;
            case "user":
                deferredRelationships.add(new DeferredRelationship(currentId, List.of(Long.parseLong(value)), "Bookmark", "User", "user"));
                break;
        }
        if (bookmarks.get(currentId) != null) {
            bookmarks.put(currentId, bookmarkRepository.save(bookmarks.get(currentId)));
        }
    }

    // ---------------- AUTHTOKEN ----------------
    private void processAuthToken(String type, String value) {
        switch (type) {
            case "id":
                currentId = Long.parseLong(value);
                authTokens.put(currentId, AuthToken.builder().build()); // no .id()
                break;
            case "user":
                deferredRelationships.add(new DeferredRelationship(currentId, List.of(Long.parseLong(value)), "AuthToken", "User", "user"));
                break;
            case "token":
                authTokens.get(currentId).setToken(UUID.fromString(value));
                break;
            case "createdAt":
                try {
                    LocalDateTime ldt = LocalDateTime.parse(value);
                    Date date = java.sql.Timestamp.valueOf(ldt);
                    authTokens.get(currentId).setCreatedAt(date);
                } catch (Exception e) {
                    log.error("Invalid date format for AuthToken createdAt: " + value, e);
                }
                break;
            case "expiresAt":
                try {
                    LocalDateTime ldt = LocalDateTime.parse(value);
                    Date date = java.sql.Timestamp.valueOf(ldt);
                    authTokens.get(currentId).setExpiresAt(date);
                } catch (Exception e) {
                    log.error("Invalid date format for AuthToken expiresAt: " + value, e);
                }
                break;
        }
        if (authTokens.get(currentId) != null) {
            authTokens.put(currentId, authTokenRepository.save(authTokens.get(currentId)));
        }
    }

    // ---------------- POST ----------------
    private void processPost(String type, String value) {
        switch (type) {
            case "id":
                currentId = Long.parseLong(value);
                posts.put(currentId, Post.builder()
                        .imageURL(new ArrayList<>())
                        .ownership(new ArrayList<>())
                        .product(new ArrayList<>())
                        .build()); // no .id()
                break;
            case "ownership":
                deferredRelationships.add(new DeferredRelationship(currentId, List.of(Long.parseLong(value)), "Post", "Ownership", "ownership"));
                break;
            case "product":
                List<Long> productIds = Arrays.stream(value.split(";")).map(Long::parseLong).toList();
                deferredRelationships.add(new DeferredRelationship(currentId, productIds, "Post", "Product", "product"));
                break;
            case "description": posts.get(currentId).setDescription(value); break;
            case "imageURL": posts.get(currentId).setImageURL(new ArrayList<>(Arrays.asList(value.split(";")))); break;
        }
        if (posts.get(currentId) != null) {
            posts.put(currentId, postRepository.save(posts.get(currentId)));
        }
    }


    // ---------------- RELATIONSHIP HANDLING ----------------
    private Object fetchEntityByTypeAndId(String entityType, Long id) {
        return switch (entityType) {
            case "User" -> userRepository.findById(id).orElseThrow();
            case "Product" -> productRepository.findById(id).orElseThrow();
            case "Ownership" -> ownershipRepository.findById(id).orElseThrow();
            case "Bookmark" -> bookmarkRepository.findById(id).orElseThrow();
            case "AuthToken" -> authTokenRepository.findById(id).orElseThrow();
            case "Post" -> postRepository.findById(id).orElseThrow();
            default -> throw new RuntimeException("Unknown entity type: " + entityType);
        };
    }

    private void saveEntity(Object entity, String entityType) {
        switch (entityType) {
            case "User": userRepository.save((User) entity); break;
            case "Product": productRepository.save((Product) entity); break;
            case "Ownership": ownershipRepository.save((Ownership) entity); break;
            case "Bookmark": bookmarkRepository.save((Bookmark) entity); break;
            case "AuthToken": authTokenRepository.save((AuthToken) entity); break;
            case "Post": postRepository.save((Post) entity); break;
        }
    }

    private void linkEntities(Object mainEntity, Object relatedEntity, String mainEntityType, String relationType) {
        switch (mainEntityType) {
            case "Ownership":
                if ("User".equals(relationType)) {
                    Ownership ownership = (Ownership) mainEntity;
                    User user = (User) relatedEntity;
                    ownership.getUser().add(user);
                } else if ("Product".equals(relationType)) {
                    Ownership ownership = (Ownership) mainEntity;
                    Product product = (Product) relatedEntity;
                    ownership.getProduct().add(product);
                }
                break;

            case "Bookmark":
                if ("User".equals(relationType)) {
                    Bookmark bookmark = (Bookmark) mainEntity;
                    User user = (User) relatedEntity;
                    bookmark.setUser(user);
                } else if ("Product".equals(relationType)) {
                    Bookmark bookmark = (Bookmark) mainEntity;
                    Product product = (Product) relatedEntity;
                    bookmark.setProduct(product);
                }
                break;

            case "AuthToken":
                if ("User".equals(relationType)) {
                    AuthToken token = (AuthToken) mainEntity;
                    User user = (User) relatedEntity;
                    token.setUser(user);
                }
                break;

            case "Post":
                if ("Ownership".equals(relationType)) {
                    Post post = (Post) mainEntity;
                    Ownership ownership = (Ownership) relatedEntity;
                    post.getOwnership().add(ownership);
                } else if ("Product".equals(relationType)) {
                    Post post = (Post) mainEntity;
                    Product product = (Product) relatedEntity;
                    post.getProduct().add(product);
                }
                break;

            default:
                log.warn("Unknown relationship type between " + mainEntityType + " and " + relationType);
        }
    }

    private void processDeferredRelationships() {
        for (DeferredRelationship relationship : deferredRelationships) {
            try {
                Long mainId = relationship.getMainId();
                List<Long> relationIds = relationship.getRelationId();
                String mainEntityType = relationship.getMainEntityType();
                String relationType = relationship.getRelationEntityType();

                Object mainEntity = fetchEntityByTypeAndId(mainEntityType, mainId);
                for (Long relationId : relationIds) {
                    Object relatedEntity = fetchEntityByTypeAndId(relationType, relationId);
                    linkEntities(mainEntity, relatedEntity, mainEntityType, relationType);
                }
                saveEntity(mainEntity, mainEntityType);
            } catch (Exception e) {
                log.error("Error processing deferred relationship for main ID: " + relationship.getMainId(), e);
            }
        }
        log.info("Deferred relationships processed.");
    }

    @PreDestroy
    public void destroy() {
        log.info("Cleaning up resources");
    }
}
