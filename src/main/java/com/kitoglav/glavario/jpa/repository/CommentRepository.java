package com.kitoglav.glavario.jpa.repository;

import com.kitoglav.glavario.jpa.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByParentPostId(long postId, Pageable pageable);

}
