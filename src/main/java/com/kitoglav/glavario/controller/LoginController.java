package com.kitoglav.glavario.controller;

import com.kitoglav.glavario.jwt.CookieData;
import com.kitoglav.glavario.jwt.JwtComponent;
import com.kitoglav.glavario.rest.dtos.LoginDto;
import com.kitoglav.glavario.rest.dtos.RegisterDto;
import com.kitoglav.glavario.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final JwtComponent jwtComponent;

    @PostMapping("/register")
    public RedirectView registerUser(@Valid RegisterDto dto, HttpServletResponse response) {
        userService.addUser(dto.getUsername(), dto.getPassword(), dto.getPasswordConfirm()).respond(response);
        return new RedirectView("/");
    }

    @PostMapping("/login")
    public RedirectView loginUser(@Valid LoginDto dto, HttpServletResponse response) {
        userService.loginUser(dto.getUsername(), dto.getPassword()).respond(response);
        return new RedirectView("/");
    }

    @PostMapping("/logout")
    public void logoutUser(HttpServletResponse response) {
        CookieData.NO_COOKIE.respond(response);
    }
}
