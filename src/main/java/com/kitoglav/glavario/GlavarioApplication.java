package com.kitoglav.glavario;

import com.kitoglav.glavario.services.FeedService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ImportResource("classpath:static/beans.xml")
@PropertySource("classpath:config/application-secrets.properties")
public class GlavarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlavarioApplication.class, args);
    }

}
