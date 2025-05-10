package com.kitoglav.glavario.controller;

import com.kitoglav.glavario.jpa.models.user.User;
import com.kitoglav.glavario.jwt.JwtComponent;
import com.kitoglav.glavario.rest.ApiResponseException;
import com.kitoglav.glavario.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final JwtComponent jwtComponent;
    private final UserService userService;

    @GetMapping("/profile")
    public String profile(@CookieValue(name = "jwt", required = false) String token, Model model) {
        User user = jwtComponent. getByToken(token);
        model.addAttribute("auth", user != null);
        if(user != null) {
            model.addAttribute("username", user.getUsername());
            model.addAttribute("roles", user.getAuthorities());
        }
        return "profile";
    }
}
