package com.example.url_shortener.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UrlMappingDto {
    private UUID id;
    private String shortUrl;
    private String longUrl;
}


