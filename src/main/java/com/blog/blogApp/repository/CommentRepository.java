package com.blog.blogApp.repository;

import com.blog.blogApp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    // Custom query
    List<Comment> findByPostId(Long PostId);
}
