package com.kitoglav.glavario.jpa.models;

import com.kitoglav.glavario.ApplicationConstants;
import com.kitoglav.glavario.api.IJpaToDto;
import com.kitoglav.glavario.jpa.models.user.UserContent;
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_content_id")
    private UserContent userContent;

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
