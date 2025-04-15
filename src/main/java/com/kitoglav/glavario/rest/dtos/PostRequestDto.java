package com.kitoglav.glavario.rest.dtos;

import com.kitoglav.glavario.ApplicationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostRequestDto {
    @NotBlank(message = "Текст поста пустой")
    @Size(max = ApplicationConstants.POST_MAX_LENGTH, message = "Текст поста слишком длинный")
    private String content;
}
