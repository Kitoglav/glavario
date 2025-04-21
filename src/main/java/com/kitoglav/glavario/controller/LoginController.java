package com.kitoglav.glavario.controller;

import com.kitoglav.glavario.jwt.CookieData;
import com.kitoglav.glavario.rest.dtos.LoginDto;
import com.kitoglav.glavario.rest.dtos.RegisterDto;
import com.kitoglav.glavario.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid RegisterDto dto) {
        return userService.addUser(dto.getUsername(), dto.getPassword(), dto.getPasswordConfirm()).toResponse();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid LoginDto dto) {
        return userService.loginUser(dto.getUsername(), dto.getPassword()).toResponse();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        return CookieData.NO_COOKIE.toResponse();
    }
}
