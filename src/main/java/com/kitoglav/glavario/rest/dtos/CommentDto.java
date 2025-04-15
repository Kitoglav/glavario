package com.kitoglav.glavario.rest.dtos;

import com.kitoglav.glavario.jpa.models.Comment;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentDto {
    private long id;
    private String content;
    private Timestamp postTime;
    private long parentPostId;
}
