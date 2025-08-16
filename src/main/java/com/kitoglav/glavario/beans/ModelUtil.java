package com.kitoglav.glavario.beans;

import com.kitoglav.glavario.jpa.models.Post;
import com.kitoglav.glavario.jpa.models.user.User;
import com.kitoglav.glavario.jwt.JwtComponent;
import com.kitoglav.glavario.rest.dtos.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
@RequiredArgsConstructor
public class ModelUtil {

    private final JwtComponent jwtComponent;

    public boolean setUser(Model model, String token) {
        User user = jwtComponent.getByToken(token);
        model.addAttribute("auth", user != null);
        if (user == null) return false;
        model.addAttribute("username", user.getUsername());
        model.addAttribute("roles", user.getAuthorities());
        return true;
    }

    public void setPosts(Model model, Page<PostDto> posts) {
        model.addAttribute("posts", posts);
    }
}
