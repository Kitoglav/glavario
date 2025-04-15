package com.kitoglav.glavario.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.lang.NonNull;
import org.springframework.web.ErrorResponse;

@Getter
@AllArgsConstructor
public class ApiResponseException extends RuntimeException implements ErrorResponse {
    private final HttpStatusCode statusCode;
    private final String message;

    @NonNull
    @Override
    public ProblemDetail getBody() {
        return ProblemDetail.forStatusAndDetail(statusCode, message);
    }

    @Override
    public String getTypeMessageCode() {
        return ErrorResponse.super.getTypeMessageCode();
    }
}
