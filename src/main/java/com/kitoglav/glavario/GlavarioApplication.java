package com.kitoglav.glavario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:static/beans.xml")
public class GlavarioApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(GlavarioApplication.class, args);
    }

}
