package com.kitoglav.glavario.rest.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private long id;
    private String username;
    private String password;
    private Set<RoleDto> roles;
}
