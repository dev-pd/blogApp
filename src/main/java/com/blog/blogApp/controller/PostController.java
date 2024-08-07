package com.blog.blogApp.controller;

import com.blog.blogApp.payload.PostDTO;
import com.blog.blogApp.payload.PostResponseDTO;
import com.blog.blogApp.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.blog.blogApp.utils.AppConstants.*;

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
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
       PostDTO createdPostDTO = postService.createPost(postDTO);
       return new ResponseEntity<>(createdPostDTO, HttpStatus.CREATED);
    }

    //Get all posts
    @GetMapping
    public ResponseEntity<PostResponseDTO> getAllPosts(
            @RequestParam(value = DEFAULT_PAGE_NUMBER, defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = DEFAULT_PAGE_SIZE, defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = DEFAULT_SORT_BY, defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = DEFAULT_SORT_ORDER, defaultValue = "asc", required = false) String sortOrder
    ){
        PostResponseDTO allPostsDTO = postService.getAllPosts(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(allPostsDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable long id){
        PostDTO postDTO = postService.getPostById(id);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    //@Valid enables validator for the PostDTO class
    public ResponseEntity<PostDTO> updatePost(@PathVariable long id, @Valid @RequestBody PostDTO postDTO){
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
