package com.kitoglav.glavario.jpa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kitoglav.glavario.ApplicationConstants;
import com.kitoglav.glavario.api.IJpaToDto;
import com.kitoglav.glavario.rest.dtos.CommentDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment implements IJpaToDto<CommentDto> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Timestamp postTime;
    @Column(nullable = false, length = ApplicationConstants.COMMENT_MAX_LENGTH)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", nullable = false)
    private Post parentPost;

    @Override
    public CommentDto convert() {
        CommentDto dto = new CommentDto();
        dto.setId(this.id);
        dto.setContent(this.content);
        dto.setPostTime(this.postTime);
        dto.setParentPostId(this.parentPost.getId());
        return dto;
    }
}
