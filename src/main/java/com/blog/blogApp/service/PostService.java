package com.blog.blogApp.service;

import com.blog.blogApp.payload.PostDTO;
import com.blog.blogApp.payload.PostResponseDTO;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    PostResponseDTO getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    PostDTO getPostById(Long id);
    PostDTO updatePost(PostDTO postDTO, Long id);
    void deletePosts();
    void deletePost(Long id);
}
