package com.example.url_shortener.application.errors;



public class ShortCodeCollisionException extends RuntimeException {
    public static final String ERROR_CODE = "SHORT_CODE_COLLISION";

    public ShortCodeCollisionException(int maxRetries) {
        super("Failed to generate unique short code after " + maxRetries + " attempts");
    }

}
