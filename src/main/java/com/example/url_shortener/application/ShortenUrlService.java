package com.example.url_shortener.application;

import java.util.zip.CRC32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.url_shortener.UrlEntry;
import com.example.url_shortener.UrlEntryRepository;

@Service
public class ShortenUrlService {

    @Autowired
    private UrlEntryRepository urlEntryRepository;

    public String execute(String longUrl) {

        CRC32 crc = new CRC32();
        crc.update(longUrl.getBytes());
        String enc = Long.toHexString(crc.getValue());


        UrlEntry urlEntry = new UrlEntry();
        urlEntry.setLongUrl(longUrl);
        // TODO Need to consider collision
        urlEntry.setShortUrl(enc);

        urlEntryRepository.save(urlEntry);
        return "OK";
    }

}
