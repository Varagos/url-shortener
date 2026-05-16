package com.example.url_shortener.dtos;

import com.example.url_shortener.validation.ValidUrl;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ShortenUrlRequest(@NotBlank(message = "URL is required") @Size(max = 2048,
        message = "URL must not exceed 2048 characters") @ValidUrl() String url) {
}
