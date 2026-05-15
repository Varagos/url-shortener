package com.example.url_shortener.application;

import java.util.zip.CRC32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.url_shortener.domain.UrlMapping;
import com.example.url_shortener.infra.UrlEntryRepository;

@Service
public class ShortenUrlService {

    @Autowired
    private UrlEntryRepository urlEntryRepository;

    public String execute(String longUrl) {

        // Abstract this using builder pattern
        CRC32 crc = new CRC32();
        crc.update(longUrl.getBytes());
        String shortUrl = Long.toHexString(crc.getValue());


        UrlMapping urlEntry = new UrlMapping();
        urlEntry.setOriginalUrl(longUrl);
        // TODO Need to handle collision
        urlEntry.setShortCode(shortUrl);

        urlEntryRepository.save(urlEntry);
        return shortUrl;
    }

}
