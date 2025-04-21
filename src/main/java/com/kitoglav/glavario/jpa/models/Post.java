package com.kitoglav.glavario.jpa.models;

import com.kitoglav.glavario.ApplicationConstants;
import com.kitoglav.glavario.api.IJpaToDto;
import com.kitoglav.glavario.rest.dtos.PostDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post implements IJpaToDto<PostDto> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private Timestamp postTime;
    @Column(nullable = false, length = ApplicationConstants.POST_MAX_LENGTH)
    private String content;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setParentPost(this);
    }

    @Override
    public PostDto convert() {
        PostDto dto = new PostDto();
        dto.setId(this.id);
        dto.setContent(this.content);
        dto.setPostTime(this.postTime);
        return dto;
    }
}
