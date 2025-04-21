package com.kitoglav.glavario.rest.dtos;

import com.kitoglav.glavario.ApplicationConstants;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequestDto {
    @Min(value = 1, message = "ID поста меньше 1")
    private long id;
    @NotBlank(message = "Текст комментария пустой")
    @Size(max = ApplicationConstants.COMMENT_MAX_LENGTH, message = "Текст комментария слишком длинный")
    private String content;
}
