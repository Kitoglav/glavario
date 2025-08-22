package com.kitoglav.glavario.controller;

import com.kitoglav.glavario.beans.ModelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final ModelUtil modelUtil;

    @GetMapping("/profile")
    public String profile(@CookieValue(name = "jwt", required = false) String token, Model model) {
        boolean auth = modelUtil.setUser(model, token);
        return auth ? "profile" : "redirect:/login";
    }
}
