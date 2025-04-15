package com.kitoglav.glavario.rest.dtos;

import com.kitoglav.glavario.api.IJpaToDto;
import com.kitoglav.glavario.jpa.models.Post;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostDto {
    private long id;
    private String content;
    private Timestamp postTime;
}
