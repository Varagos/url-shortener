package com.example.url_shortener.application;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.example.url_shortener.application.errors.ShortCodeCollisionException;
import com.example.url_shortener.application.shortcode.ShortCodeGenerator;
import com.example.url_shortener.domain.UrlMapping;
import com.example.url_shortener.infra.UrlMappingRepository;

@Service
public class ShortenUrlService {

    private static final int MAX_RETRIES = 5;

    private final UrlMappingRepository urlEntryRepository;
    private final ShortCodeGenerator shortCodeGenerator;

    public ShortenUrlService(@Qualifier("base62random") ShortCodeGenerator generator,
            UrlMappingRepository urlMappingRepository) {
        this.shortCodeGenerator = generator;
        this.urlEntryRepository = urlMappingRepository;
    }

    public String execute(String originalUrl) {
        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {

            String normalizedOriginalUrl = normalizeUrl(originalUrl);

            String shortCode = shortCodeGenerator.generate(normalizedOriginalUrl);

            try {

                UrlMapping urlEntry = new UrlMapping();
                urlEntry.setOriginalUrl(normalizedOriginalUrl);
                urlEntry.setShortCode(shortCode);

                urlEntryRepository.save(urlEntry);
                return shortCode;
            } catch (DataIntegrityViolationException e) {
                // shortCode collision, retry
            }
        }
        throw new ShortCodeCollisionException(MAX_RETRIES);
    }

    private String normalizeUrl(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "https://" + url;
        }
        return url;
    }


}
