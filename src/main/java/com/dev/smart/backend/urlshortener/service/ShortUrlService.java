package com.dev.smart.backend.urlshortener.service;

import com.dev.smart.backend.common.util.Base62Encoder;
import com.dev.smart.backend.urlshortener.entity.ShortUrl;
import com.dev.smart.backend.urlshortener.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShortUrlService {

    private final ShortUrlRepository repository;

    public String createShortUrl(String originalUrl) {

        // Step 1 — Check if already shortened
        Optional<ShortUrl> existing = repository.findByOriginalUrl(originalUrl);

        if (existing.isPresent()) {
            return existing.get().getShortCode();
        }

        // Step 2 — Generate new one safely
        for (int i = 0; i < 5; i++) {

            String code = Base62Encoder.generateShortCode(6);

            try {

                ShortUrl url = ShortUrl.builder()
                        .originalUrl(originalUrl)
                        .shortCode(code)
                        .createdAt(LocalDateTime.now())
                        .build();

                repository.save(url);

                return code;

            } catch (DataIntegrityViolationException ex) {
                // collision — retry
            }
        }

        throw new RuntimeException("Unable to generate unique short URL");
    }


    public String getOriginalUrl(String code) {
        return repository.findByShortCode(code)
                .map(ShortUrl::getOriginalUrl)
                .orElseThrow(() -> new RuntimeException("URL not found"));
    }
}
