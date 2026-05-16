package com.example.url_shortener.validation;

import java.net.URI;
import java.net.URISyntaxException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidUrlValidator implements ConstraintValidator<ValidUrl, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // let @NotBlank handle emptiness
        }

        String normalized = value;
        if (!normalized.startsWith("http://") && !normalized.startsWith("https://")) {
            normalized = "https://" + normalized;
        }

        try {
            URI uri = new URI(normalized);
            return uri.getHost() != null;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
