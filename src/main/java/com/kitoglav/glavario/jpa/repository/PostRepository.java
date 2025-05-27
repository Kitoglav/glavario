package com.kitoglav.glavario.jpa.repository;

import com.kitoglav.glavario.jpa.models.Post;
import com.kitoglav.glavario.jpa.models.user.UserContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserContent(UserContent userContent);

}
