package com.blog.blogApp.controller;

import com.blog.blogApp.payload.CommentDTO;
import com.blog.blogApp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postID}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Long postID,
                                                 @RequestBody CommentDTO commentDTO) {
       CommentDTO addedCommentDTO = commentService.createComment(postID, commentDTO);
       return new ResponseEntity<>(addedCommentDTO, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postID}/comments")
    public ResponseEntity<List<CommentDTO>> getAllComments(@PathVariable Long postID) {
        List<CommentDTO> allCommentsDTO = commentService.getCommentsByPostID(postID);
        return new ResponseEntity<>(allCommentsDTO, HttpStatus.OK);
    }

    @GetMapping("/posts/{postID}/comments/{commentID}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long postID, @PathVariable Long commentID) {
        CommentDTO commentDTO = commentService.getCommentById(postID, commentID);
        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @PutMapping("/posts/{postID}/comments/{commentID}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long postID,
                                                    @PathVariable Long commentID,
                                                    @RequestBody CommentDTO commentDTO) {
        CommentDTO updatedCommentDTO = commentService.updateComment(postID, commentID, commentDTO);
        return new ResponseEntity<>(updatedCommentDTO, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postID}/comments/{commentID}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postID, @PathVariable Long commentID) {
        commentService.deleteComment(postID, commentID);
        return new ResponseEntity<>("Comment deleted", HttpStatus.OK);
    }
}
