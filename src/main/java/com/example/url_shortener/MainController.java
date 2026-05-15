package com.example.url_shortener;

import java.net.UnknownHostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.url_shortener.application.ExpandShortUrlService;
import com.example.url_shortener.application.ShortenUrlService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(path = "/")
public class MainController {

    @Autowired
    private ShortenUrlService shortenUrlService;

    @Autowired
    private ExpandShortUrlService expandShortUrlService;

    @Value("${app.base-url}")
    private String baseUrl;


    @PostMapping("shorten")
    public String shorten(@RequestParam String longUrl) throws UnknownHostException {
        String result = shortenUrlService.execute(longUrl);

        return "%s/%s".formatted(baseUrl, result);
    }


    @GetMapping("/{shortCode}")
    public String redirect(@PathVariable String shortCode) {
        String originalUrl = expandShortUrlService.execute(shortCode);
        return originalUrl;
    }
}
