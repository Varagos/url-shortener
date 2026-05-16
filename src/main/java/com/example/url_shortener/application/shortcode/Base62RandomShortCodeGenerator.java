package com.example.url_shortener.application.shortcode;

import java.security.SecureRandom;
import org.springframework.stereotype.Component;

@Component("base62random")
public class Base62RandomShortCodeGenerator implements ShortCodeGenerator {

    private static final String ALPHABET =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int CODE_LENGTH = 7;
    private final SecureRandom random = new SecureRandom();

    @Override
    public String generate(String originalUrl) {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

}
