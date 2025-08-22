package com.kitoglav.glavario.services;

import com.kitoglav.glavario.jpa.models.Comment;
import com.kitoglav.glavario.jpa.models.Post;
import com.kitoglav.glavario.jpa.models.user.User;
import com.kitoglav.glavario.jpa.repository.CommentRepository;
import com.kitoglav.glavario.jpa.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Transactional
    public Post addPost(String content, User user) {
        User persistentUser = userService.getUser(user.getId()).orElseThrow();
        Post post = new Post();
        post.setPostTime(Timestamp.from(Instant.now()));
        post.setContent(content);
        persistentUser.getUserContent().addPost(post);
        return postRepository.save(post);
    }

    @Transactional
    public Optional<Comment> addCommentTo(long postId, String content, User user) {
        return getPost(postId).map(post -> {
            User persistentUser = userService.getUser(user.getId()).orElseThrow();
            Comment comment = new Comment();
            comment.setPostTime(Timestamp.from(Instant.now()));
            comment.setContent(content);
            post.addComment(comment);
            persistentUser.getUserContent().addComment(comment);
            return comment;
        });
    }

    @Transactional(readOnly = true)
    public Page<Comment> getCommentsFor(long postId, Pageable pageable) {
        return commentRepository.findByParentPostId(postId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Post> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Post> getPost(long id) {
        return postRepository.findById(id);
    }

}
