package com.blog.blogApp.service.impl;

import com.blog.blogApp.entity.Comment;
import com.blog.blogApp.entity.Post;
import com.blog.blogApp.exception.BlogAPIException;
import com.blog.blogApp.exception.ResourceNotFoundException;
import com.blog.blogApp.payload.CommentDTO;
import com.blog.blogApp.repository.CommentRepository;
import com.blog.blogApp.repository.PostRepository;
import com.blog.blogApp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    private CommentDTO mapToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setName(comment.getName());
        commentDTO.setEmail(comment.getEmail());
        commentDTO.setBody(comment.getBody());
        return commentDTO;
    }

    private Comment mapToCommentEntity(CommentDTO commentDTO) {
        Comment commentEntity = new Comment();
        commentEntity.setId(commentDTO.getId());
        commentEntity.setName(commentDTO.getName());
        commentEntity.setEmail(commentDTO.getEmail());
        commentEntity.setBody(commentDTO.getBody());
        return commentEntity;
    }

    @Override
    public CommentDTO createComment(Long postID, CommentDTO commentDTO) {

        Comment comment = mapToCommentEntity(commentDTO);

        // Retrieve post entity by ID
        Post post = postRepository.findById(postID).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postID)
        );

        // Set post to comment entity
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        return mapToCommentDTO(savedComment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostID(Long postID) {
        Post post = postRepository.findById(postID).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postID));
        List<Comment> comments = commentRepository.findByPostId(post.getId());
        return comments.stream().map(comment -> mapToCommentDTO(comment)).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public CommentDTO getCommentById(Long postID, Long commentID) {
        // Retrieve post by ID
        Post post = postRepository.findById(postID).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postID));

        // Retrieve comment by ID
        Comment comment = commentRepository.findById(commentID).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentID));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
        }
        return mapToCommentDTO(comment);
    }

    @Override
    public CommentDTO updateComment(Long postID, Long commentID, CommentDTO comment) {

        //Retrieve the post to which the comment belongs to
        Post post = postRepository.findById(postID).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postID));

        //Retrieve the comment which needs to be updated
        Comment toUpdateComment = commentRepository.findById(commentID).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentID)
        );
        //Check if the comment belongs to the post or not
        //Throw an exception accordingly
        if(!toUpdateComment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
        }

        //Update the details of the comment accordingly
        toUpdateComment.setName(comment.getName());
        toUpdateComment.setEmail(comment.getEmail());
        toUpdateComment.setBody(comment.getBody());

        //Save the updated comment to the database
        Comment updatedComment = commentRepository.save(toUpdateComment);

        //Map the entity to the DTO and return the updated comment DTO
        return mapToCommentDTO(updatedComment);
    }

    @Override
    public Void deleteComment(Long postID, Long commentID) {

        //Retrieve the post to which the comment belongs to
        Post post = postRepository.findById(postID).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postID));

        //Retrieve the comment which needs to be updated
        Comment toDeleteComment = commentRepository.findById(commentID).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentID)
        );
        //Check if the comment belongs to the post or not
        //Throw an exception accordingly
        if(!toDeleteComment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
        }

        commentRepository.delete(toDeleteComment);
        return null;
    }
}
