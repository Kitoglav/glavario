package com.kitoglav.glavario.rest.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto {
    private long id;
    private String content;
    private LocalDateTime postTime;
    private String username;
    private List<CommentDto> comments;
}
