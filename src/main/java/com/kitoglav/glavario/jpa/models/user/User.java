package com.kitoglav.glavario.jpa.models.user;

import com.kitoglav.glavario.ApplicationConstants;
import com.kitoglav.glavario.api.IJpaToDto;
import com.kitoglav.glavario.jpa.models.Role;
import com.kitoglav.glavario.rest.dtos.UserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements IJpaToDto<UserDto>, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = ApplicationConstants.USERNAME_MAX_LENGTH)

    private String username;
    @Column(nullable = false, length = ApplicationConstants.PASSWORD_MAX_LENGTH)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private UserContent userContent;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private UserOnline userOnline;

    @Override
    public UserDto convert() {
        UserDto dto = new UserDto();
        dto.setId(this.id);
        dto.setUsername(this.username);
        dto.setPassword(this.password);
        dto.setRoles(this.getRoles().stream().map(Role::convert).collect(Collectors.toSet()));
        return dto;
    }

    public void setUserContent(UserContent userContent) {
        userContent.setUser(this);
        this.userContent = userContent;
    }

    public void setUserOnline(UserOnline userOnline) {
        userOnline.setUser(this);
        this.userOnline = userOnline;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
