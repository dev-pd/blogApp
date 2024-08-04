package com.blog.blogApp.service.impl;

import com.blog.blogApp.entity.Post;
import com.blog.blogApp.exception.ResourceNotFoundException;
import com.blog.blogApp.payload.PostDTO;
import com.blog.blogApp.payload.PostResponseDTO;
import com.blog.blogApp.repository.PostRepository;
import com.blog.blogApp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    private Post mapToEntity(PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        return post;
    }

    private PostDTO mapToDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setDescription(post.getDescription());
        postDTO.setContent(post.getContent());
        return postDTO;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = mapToEntity(postDTO);
        Post savedPost = postRepository.save(post);
        PostDTO postDTOResponse = mapToDTO(savedPost);
        return postDTOResponse;
    }

    @Override
    public PostResponseDTO getAllPosts(Integer pageNumber, Integer pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Post> posts = postRepository.findAll(pageable);
        //List<Post> posts = postRepository.findAll();

        List<Post> postList = posts.getContent();
        List<PostDTO> content = postList.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponseDTO postResponseDTO = new PostResponseDTO();
        postResponseDTO.setContent(content);
        postResponseDTO.setPageNumber(posts.getNumber());
        postResponseDTO.setPageSize(posts.getSize());
        postResponseDTO.setTotalElements(posts.getTotalElements());
        postResponseDTO.setTotalPages(posts.getTotalPages());
        postResponseDTO.setLastPage(posts.isLast());

        return postResponseDTO;
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id) {
        // get post by id from database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());

        Post savedPost = postRepository.save(post);

        PostDTO postDTOResponse = mapToDTO(savedPost);

        return postDTOResponse;
    }

    @Override
    public void deletePosts() {
        try {
            postRepository.deleteAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }
}
