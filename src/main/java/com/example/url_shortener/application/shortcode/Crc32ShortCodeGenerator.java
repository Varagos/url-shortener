package com.example.url_shortener.application.shortcode;

import java.util.zip.CRC32;
import org.springframework.stereotype.Component;

@Component("crc32")
public class Crc32ShortCodeGenerator implements ShortCodeGenerator {

    @Override
    public String generate(String originalUrl) {
        CRC32 crc = new CRC32();
        crc.update(originalUrl.getBytes());
        String shortUrl = Long.toHexString(crc.getValue());
        return shortUrl;
    }

}
