package com.kitoglav.glavario.rest.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentDto {
    private long id;
    private String content;
    private Timestamp postTime;
    private long parentPostId;
}
