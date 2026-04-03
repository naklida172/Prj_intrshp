package com.adilkan.demo.services.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adilkan.demo.dtos.PostDTO;
import com.adilkan.demo.entities.Post;
import com.adilkan.demo.entities.Product;
import com.adilkan.demo.exceptions.ResourceNotFoundException;
import com.adilkan.demo.mappers.PostMapper;
import com.adilkan.demo.repositories.PostRepository;
import com.adilkan.demo.repositories.ProductRepository;
import com.adilkan.demo.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Post createPost(PostDTO postDTO) {
        if (postDTO == null) {
            throw new IllegalArgumentException("PostDTO cannot be null.");
        }

        Post post = PostMapper.toEntity(postDTO);

        List<Product> products = new ArrayList<>();
        if (postDTO.getProductIds() != null) {
            for (Long productId : postDTO.getProductIds()) {
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
                products.add(product);
            }
        }

        post.setProduct(products);
        return postRepository.save(post);
    }

    @Override
    public Post getPostById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Post ID cannot be null.");
        }

        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        if (posts.isEmpty()) {
            throw new ResourceNotFoundException("No posts found.");
        }
        return posts;
    }

    @Override
    public Post updatePost(Long id, PostDTO postDTO) {
        if (id == null || postDTO == null) {
            throw new IllegalArgumentException("Post ID and PostDTO cannot be null.");
        }

        Post existingPost = getPostById(id);

        existingPost.setDescription(postDTO.getDescription());
        existingPost.setImageURL(postDTO.getImageURL());

        List<Product> products = new ArrayList<>();
        if (postDTO.getProductIds() != null) {
            for (Long productId : postDTO.getProductIds()) {
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
                products.add(product);
            }
        }
        existingPost.setProduct(products);

        return postRepository.save(existingPost);
    }

    @Override
    public void deletePost(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Post ID cannot be null.");
        }

        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found with id: " + id);
        }

        postRepository.deleteById(id);
    }
}