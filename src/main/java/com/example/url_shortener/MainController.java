package com.example.url_shortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.url_shortener.application.ShortenUrlService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(path = "/")
public class MainController {

    @Autowired
    private UrlEntryRepository urlEntryRepository;

    @Autowired
    private ShortenUrlService shortenUrlService;

    @PostMapping("shorten")
    public String shorten(@RequestBody String longUrl) {
        String result = shortenUrlService.execute(longUrl);
        return result;
    }

    @GetMapping("/all")
    public Iterable<UrlEntry> getMethodName() {
        return urlEntryRepository.findAll();
    }
}
