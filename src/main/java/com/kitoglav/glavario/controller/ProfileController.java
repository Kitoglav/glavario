package com.kitoglav.glavario.controller;

import com.kitoglav.glavario.jpa.models.User;
import com.kitoglav.glavario.jwt.JwtComponent;
import com.kitoglav.glavario.rest.ApiResponseException;
import com.kitoglav.glavario.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final JwtComponent jwtComponent;
    private final UserService userService;

    @GetMapping("/profile")
    public String profile(@CookieValue(name = "jwt", required = false) String token, Model model) {
        if (token == null || !jwtComponent.validateToken(token)) {
            throw new ApiResponseException(HttpStatus.UNAUTHORIZED, "Недействительный токен");
        }
        String username = jwtComponent.getUsernameFromToken(token);
        User user = userService.getUser(username)
                .orElseThrow(() -> new ApiResponseException(HttpStatus.INTERNAL_SERVER_ERROR, "Пользователь с именем {%S} не найден".formatted(username)));
        model.addAttribute("auth", user != null);
        if(user != null) {
            model.addAttribute("username", user.getUsername());
            model.addAttribute("roles", user.getAuthorities());
        }
        return "profile";
    }
}
