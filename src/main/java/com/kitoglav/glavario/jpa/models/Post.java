package com.kitoglav.glavario.jpa.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue
    private long id;

    private Timestamp postTime;

    private String content;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentPost")
    private Set<Comment> comments;
}
