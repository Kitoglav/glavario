package com.kitoglav.glavario.services;

import com.kitoglav.glavario.jpa.models.Post;
import com.kitoglav.glavario.jpa.models.Role;
import com.kitoglav.glavario.jpa.models.user.User;
import com.kitoglav.glavario.jpa.models.user.UserContent;
import com.kitoglav.glavario.jpa.models.user.UserOnline;
import com.kitoglav.glavario.jpa.repository.RoleRepository;
import com.kitoglav.glavario.jpa.repository.UserRepository;
import com.kitoglav.glavario.jwt.CookieData;
import com.kitoglav.glavario.jwt.JwtComponent;
import com.kitoglav.glavario.rest.ApiResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtComponent jwtComponent;

    public Optional<User> getUser(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public Set<Role> getRolesFor(long id) {
        return roleRepository.findRolesByUsersId(id);
    }

    @Transactional(readOnly = true)
    public Set<Role> getRolesFor(String username) {
        return roleRepository.findRolesByUsersUsername(username);
    }

    @Transactional
    public CookieData addUser(String username, String password, String passwordConfirm) {
        if (userRepository.existsByUsername(username)) {
            throw new ApiResponseException(HttpStatus.BAD_REQUEST, "Пользователь с именем {%s} уже существует".formatted(username));
        }
        if (!password.equals(passwordConfirm)) {
            throw new ApiResponseException(HttpStatus.BAD_REQUEST, "Повторный пароль введён неверно");
        }
        User user = new User();
        user.setUsername(username);
        do {
            password = passwordEncoder.encode(password);
        } while (passwordEncoder.upgradeEncoding(password));
        user.setPassword(password);
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new ApiResponseException(HttpStatus.INTERNAL_SERVER_ERROR, "Нет подходящей роли: {%s}".formatted("ROLE_USER")));
        user.setRoles(Collections.singleton(role));
        user.setUserContent(new UserContent());
        UserOnline userOnline = new UserOnline();
        userOnline.setRegisterTime(Timestamp.from(Instant.now()));
        userOnline.setLastLoginTime(Timestamp.from(Instant.now()));
        user.setUserOnline(userOnline);
        userRepository.save(user);
        return generateCookieData(user);
    }

    @Transactional
    public CookieData loginUser(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return generateCookieData(userDetails);
        } catch (BadCredentialsException e) {
            throw new ApiResponseException(HttpStatus.UNAUTHORIZED, "Bad credentials");
        }
    }

    @Transactional
    public void updateUserOnline(User user) {
        getUser(user.getId()).ifPresent(persistentUser -> persistentUser.getUserOnline().setLastLoginTime(Timestamp.from(Instant.now())));
    }

    public ResponseCookie createCookie(String token) {
        return ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofHours(1))
                .sameSite("Strict")
                .build();
    }

    private CookieData generateCookieData(UserDetails user, String token) {
        return CookieData.generate(user, jwtComponent, createCookie(token));
    }
    public CookieData generateCookieData(UserDetails user) {
        String token = jwtComponent.generateToken(user);
        return generateCookieData(user, token);
    }
}
