package com.blog.blogApp.service;

import com.blog.blogApp.payload.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(Long postID, CommentDTO comment);
    List<CommentDTO> getCommentsByPostID(Long postID);
    CommentDTO getCommentById(Long postID, Long commentID);
    CommentDTO updateComment(Long postID, Long commentID, CommentDTO comment);
    String deleteComment(Long postID, Long commentID);
}
