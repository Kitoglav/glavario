package com.kitoglav.glavario.rest.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private long id;
    private String content;
    private LocalDateTime postTime;
    private long parentPostId;
    private String username;
}
