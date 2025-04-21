package com.kitoglav.glavario.rest.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostDto {
    private long id;
    private String content;
    private Timestamp postTime;
}
