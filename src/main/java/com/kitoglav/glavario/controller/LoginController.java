package com.kitoglav.glavario.controller;

import com.kitoglav.glavario.jwt.CookieData;
import com.kitoglav.glavario.rest.ApiResponseException;
import com.kitoglav.glavario.rest.dtos.LoginDto;
import com.kitoglav.glavario.rest.dtos.RegisterDto;
import com.kitoglav.glavario.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @PostMapping("/register")
    public String registerUser(@Valid RegisterDto dto, HttpServletResponse response, RedirectAttributes attr) {
        try {
            userService.addUser(dto.getUsername(), dto.getPassword(), dto.getPasswordConfirm()).respond(response);
        } catch (ApiResponseException e) {
            attr.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
        return "redirect:/home";
    }

    @PostMapping("/login")
    public String loginUser(@Valid LoginDto dto, HttpServletResponse response, RedirectAttributes attr) {
        try {
            userService.loginUser(dto.getUsername(), dto.getPassword()).respond(response);
        } catch (ApiResponseException e) {
            attr.addFlashAttribute("error", e.getMessage());
            return "redirect:/login";
        }
        return "redirect:/home";
    }

    @PostMapping("/logout")
    public String logoutUser(HttpServletResponse response) {
        CookieData.NO_COOKIE.respond(response);
        return "redirect:/home";
    }
}
