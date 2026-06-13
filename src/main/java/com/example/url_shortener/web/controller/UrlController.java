package com.example.url_shortener.web.controller;

import com.example.url_shortener.application.ShortenUrlService;
import com.example.url_shortener.domain.UrlMapping;
import com.example.url_shortener.infra.UrlMappingRepository;
import com.example.url_shortener.web.model.OriginUrl;
import com.example.url_shortener.web.model.UrlMappingDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/urls")
public class UrlController {

    private UrlMappingRepository urlMappingRepository;
    private ShortenUrlService shortenUrlService;
    @Value("${app.base-url}")
    private String baseUrl;



    public UrlController(UrlMappingRepository urlMappingRepository, ShortenUrlService shortenUrlService) {
        this.urlMappingRepository = urlMappingRepository;
        this.shortenUrlService = shortenUrlService;
    }


    @GetMapping
    public String home(Model model) {
        model.addAttribute("urlInput", new OriginUrl());

        return "home";
    }

    @PostMapping("shorten")
    public String shorten(@ModelAttribute OriginUrl originUrl, Model model) {

        System.out.println("originURL received" + originUrl.toString());
        System.out.println("originURL received" + originUrl.getLongUrl());
//        TOOD Validate etc
        String originalUrl = originUrl.getLongUrl();
        String result = shortenUrlService.execute(originalUrl);

        String shortUrl = "%s/%s".formatted(baseUrl, result);

        model.addAttribute("urlMapping", new UrlMappingDto(UUID.randomUUID(), shortUrl, originalUrl));

        return "shortened-url";
    }


    @GetMapping("all")
    public String getUrls(Model model) {
        Iterable<UrlMapping> urlMappingEntities = urlMappingRepository.findAll();
        List<UrlMappingDto> urlMappingDtos = new ArrayList<>();

        for (UrlMapping urlMapping : urlMappingEntities) {
            urlMappingDtos.add(new UrlMappingDto(
                    urlMapping.getId(), urlMapping.getShortCode(), urlMapping.getOriginalUrl()
            ));
        }
        model.addAttribute("urlMappings", urlMappingDtos);

        return "all-urls";
    }
}
