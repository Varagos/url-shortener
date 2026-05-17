package com.example.url_shortener.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.url_shortener.application.errors.ShortUrlNotFoundException;
import com.example.url_shortener.domain.UrlMapping;
import com.example.url_shortener.infra.UrlMappingRepository;

@Service
public class ExpandShortUrlService {

    private UrlMappingRepository urlMappingRepository;

    public ExpandShortUrlService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    public String execute(String shortUrl) {
        UrlMapping urlEntry = urlMappingRepository.findByShortCode(shortUrl)
                .orElseThrow(() -> new ShortUrlNotFoundException(shortUrl));

        return urlEntry.getOriginalUrl();

    }

}
