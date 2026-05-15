package com.example.url_shortener.application;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.url_shortener.domain.UrlMapping;
import com.example.url_shortener.infra.UrlEntryRepository;

@Service
public class ExpandShortUrlService {

    @Autowired
    private UrlEntryRepository urlEntryRepository;

    public String execute(String shortUrl) {
        Optional<UrlMapping> urlEntries = urlEntryRepository.findByShortCode(shortUrl);
        // What if empty
        UrlMapping urlEntry = urlEntries.get();
        return urlEntry.getOriginalUrl();

    }

}
