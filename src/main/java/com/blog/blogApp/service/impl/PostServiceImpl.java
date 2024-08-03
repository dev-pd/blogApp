package com.blog.blogApp.service.impl;

import com.blog.blogApp.entity.Post;
import com.blog.blogApp.payload.PostDTO;
import com.blog.blogApp.repository.PostRepository;
import com.blog.blogApp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {

        //The DTO is received and then it is converted to Entity
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        //Entity is then saved to the DB
        //Returns an Entity which is saved
        Post savedPost = postRepository.save(post);

        //The saved Entity is then converted to DTO to transfer
        //Decouple DTO and Entity
        PostDTO postDTOResponse = new PostDTO();
        postDTOResponse.setId(savedPost.getId());
        postDTOResponse.setTitle(savedPost.getTitle());
        postDTOResponse.setDescription(savedPost.getDescription());
        postDTOResponse.setContent(savedPost.getContent());

        return postDTOResponse;
    }
}
