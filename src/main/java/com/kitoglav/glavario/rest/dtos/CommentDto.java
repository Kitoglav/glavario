package com.kitoglav.glavario.rest.dtos;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

@Data
public class CommentDto {
    private long id;
    private String content;
    private LocalDateTime postTime;
    private long parentPostId;
    private String username;
}
