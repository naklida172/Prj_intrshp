package com.adilkan.demo.services;

import java.util.List;

import com.adilkan.demo.dtos.PostDTO;
import com.adilkan.demo.entities.Post;

public interface PostService {
    Post createPost(PostDTO postDTO);
    Post getPostById(Long id);
    List<Post> getAllPosts();
    Post updatePost(Long id, PostDTO postDTO);
    void deletePost(Long id);
}
