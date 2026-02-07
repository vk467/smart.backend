package com.dev.smart.backend.urlshortener.controller;

import com.dev.smart.backend.urlshortener.dto.ShortenRequest;
import com.dev.smart.backend.urlshortener.service.ShortUrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/url")
@RequiredArgsConstructor
public class ShortUrlController {

    private final ShortUrlService service;

    /**
     * Create Short URL
     */
    @PostMapping("/shorten")
    public ResponseEntity<?> createShortUrl(
            @Valid @RequestBody ShortenRequest request) {

        String code = service.createShortUrl(request.getUrl());

        String shortUrl = "http://localhost:8080/" + code;

        return ResponseEntity.ok(
                Map.of(
                        "shortUrl", shortUrl,
                        "code", code
                )
        );
    }

    /**
     * Redirect to Original URL
     */
    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(
            @PathVariable String code) {

        String originalUrl = service.getOriginalUrl(code);

        return ResponseEntity
                .status(302)
                .location(URI.create(originalUrl))
                .build();
    }
}
