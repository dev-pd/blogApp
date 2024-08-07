package com.blog.blogApp.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {
    private Long id;

    //Title should not be empty or not null
    //Title should have at least 2 characters
    @NotEmpty(message = "Title should not be empty")
    @Size(min = 2, max = 100, message = "Title should have at least 2 characters")
    private String title;

    //Description should not be empty or null
    //Description should have at least 5 characters
    @NotEmpty(message = "Description should not be empty")
    @Size(min = 5, max = 100, message = "Description should have at least 5 characters")
    private String description;

    //Content should not be empty or null
    //Content should have at least 5 characters
    @NotEmpty(message = "Content should not be empty")
    @Size(min = 5, max = 100, message = "Content should have at least 5 characters")
    private String content;

    private Set<CommentDTO> comments;
}