package com.kitoglav.glavario.controller;

import com.kitoglav.glavario.beans.ModelUtil;
import com.kitoglav.glavario.jpa.models.Post;
import com.kitoglav.glavario.services.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ModelUtil modelUtil;
    private final FeedService feedService;
    @GetMapping("/home")
    public String home(@CookieValue(name = "jwt", required = false) String token, Model model, @PageableDefault(sort = "postTime", direction = Sort.Direction.DESC, size = 20) Pageable pageable) {
        modelUtil.setUser(model, token);
        modelUtil.setPosts(model, feedService.getPosts(pageable).map(Post::convert));
        return "home";
    }

    @GetMapping("/")
    public String blank() {
        return "redirect:/home";
    }
}
