package com.kitoglav.glavario;

import com.kitoglav.glavario.jpa.repository.PostRepository;
import com.kitoglav.glavario.services.FeedService;
import com.kitoglav.glavario.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import javax.crypto.SecretKey;

@SpringBootTest
@TestConfiguration
class GlavarioApplicationTests {

    @Autowired
    private FeedService feedService;
    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        userService.getUser(3).ifPresent(user -> {
            feedService.addPost("Новый пост", user);
        });
    }

}
