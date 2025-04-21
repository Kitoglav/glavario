package com.kitoglav.glavario;

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
    private UserService userService;

    @Test
    void contextLoads() {
        SecretKey key = Jwts.SIG.HS512.key().build();
        String base64Key = Encoders.BASE64.encode(key.getEncoded());
        System.out.println(base64Key);
    }

}
