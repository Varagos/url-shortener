package com.example.url_shortener.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.url_shortener.UrlEntry;
import com.example.url_shortener.UrlEntryRepository;

@Service
public class ShortenUrlService {

    @Autowired
    private UrlEntryRepository urlEntryRepository;

    public String execute(String longUrl) {

        UrlEntry urlEntry = new UrlEntry();
        urlEntry.setLongUrl(longUrl);
        urlEntry.setShortUrl("todo");

        urlEntryRepository.save(urlEntry);
        return "OK";
    }

}
