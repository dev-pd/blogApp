package com.blog.blogApp.controller;

import com.blog.blogApp.payload.PostDTO;
import com.blog.blogApp.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    //Loose-coupling
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //Create a blog post
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO){
       PostDTO createdPost = postService.createPost(postDTO);
       return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
}
