package com.dev.smart.backend.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShortenRequest {

    @NotBlank(message = "URL cannot be empty")
    private String url;
}
