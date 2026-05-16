package com.example.url_shortener.application.errors;



public class ShortUrlNotFoundException extends RuntimeException {
    public static final String ERROR_CODE = "URL_NOT_FOUND";

    public ShortUrlNotFoundException(String shortCode) {
        super("Short URL not found: " + shortCode);
    }

}
