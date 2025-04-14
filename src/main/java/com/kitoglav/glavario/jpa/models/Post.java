package com.kitoglav.glavario.jpa.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private Timestamp postTime;
    @Column(nullable = false, length = 2500)
    private String content;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentPost", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("parentPost")
    private Set<Comment> comments;

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setParentPost(this);
    }
}
