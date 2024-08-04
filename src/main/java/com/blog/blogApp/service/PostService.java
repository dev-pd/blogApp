package com.blog.blogApp.service;

import com.blog.blogApp.payload.PostDTO;
import com.blog.blogApp.payload.PostResponseDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    PostResponseDTO getAllPosts(Integer pageNumber, Integer pageSize);
    PostDTO getPostById(Long id);
    PostDTO updatePost(PostDTO postDTO, Long id);
    void deletePosts();
    void deletePost(Long id);
}
