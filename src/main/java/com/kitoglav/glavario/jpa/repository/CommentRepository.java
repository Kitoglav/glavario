package com.kitoglav.glavario.jpa.repository;

import com.kitoglav.glavario.jpa.models.Comment;
import com.kitoglav.glavario.jpa.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
