package com.blog.blogApp.controller;

import com.blog.blogApp.payload.PostDTO;
import com.blog.blogApp.payload.PostResponseDTO;
import com.blog.blogApp.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
       PostDTO createdPostDTO = postService.createPost(postDTO);
       return new ResponseEntity<>(createdPostDTO, HttpStatus.CREATED);
    }

    //Get all posts
    @GetMapping
    public ResponseEntity<PostResponseDTO> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        PostResponseDTO allPostsDTO = postService.getAllPosts(pageNumber, pageSize);
        return new ResponseEntity<>(allPostsDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable long id){
        PostDTO postDTO = postService.getPostById(id);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable long id, @RequestBody PostDTO postDTO){
        PostDTO postResponseDTO = postService.updatePost(postDTO, id);
        return new ResponseEntity<>(postResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping
    public String deletePost(){
        postService.deletePosts();
        return "Deleted All Posts";
    }

    @DeleteMapping("/{id}")
    public String deletePostById(@PathVariable long id){
        postService.deletePost(id);
        return "Deleted Post";
    }
}
