package com.kitoglav.glavario.jpa.models;

import com.kitoglav.glavario.api.IJpaToDto;
import com.kitoglav.glavario.rest.dtos.RoleDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority, IJpaToDto<RoleDto> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private Set<User> users;

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public RoleDto convert() {
        RoleDto dto = new RoleDto();
        dto.setAuthority(this.name);
        return dto;
    }
}
