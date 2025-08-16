package com.kitoglav.glavario.rest.dtos;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.List;

@Data
public class PostDto {
    private long id;
    private String content;
    private LocalDateTime postTime;
    private String username;
    private List<CommentDto> comments;
}
