package com.kitoglav.glavario.rest.dtos;

import lombok.Data;

@Data
//TODO: valid
public class RegisterDto {
    private String username;
    private String password;
    private String passwordConfirm;
}
