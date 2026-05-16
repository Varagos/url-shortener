package com.example.url_shortener;

import java.net.UnknownHostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.url_shortener.application.ExpandShortUrlService;
import com.example.url_shortener.application.ShortenUrlService;
import com.example.url_shortener.application.errors.ApiError;
import com.example.url_shortener.application.errors.ShortCodeCollisionException;
import com.example.url_shortener.application.errors.ShortUrlNotFoundException;
import com.example.url_shortener.dtos.ShortenUrlRequest;
import com.example.url_shortener.dtos.ShortenUrlResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public ShortenUrlResponse shorten(@Valid @RequestBody ShortenUrlRequest shortenUrlRequest) {

        String result = shortenUrlService.execute(shortenUrlRequest.url());

        String shortUrl = "%s/%s".formatted(baseUrl, result);
        return new ShortenUrlResponse(shortUrl);
    }


    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = expandShortUrlService.execute(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND) // 302
                .header(HttpHeaders.LOCATION, originalUrl).build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ShortUrlNotFoundException.class)
    public ApiError handleNotFound(ShortUrlNotFoundException e) {
        return new ApiError(ShortUrlNotFoundException.ERROR_CODE, e.getMessage());
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(ShortCodeCollisionException.class)
    public ApiError handleCollision(ShortCodeCollisionException e) {
        return new ApiError(ShortCodeCollisionException.ERROR_CODE, e.getMessage());
    }
}
