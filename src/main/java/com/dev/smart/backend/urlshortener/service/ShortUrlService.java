package com.dev.smart.backend.urlshortener.service;

import com.dev.smart.backend.common.util.Base62Encoder;
import com.dev.smart.backend.urlshortener.entity.ShortUrl;
import com.dev.smart.backend.urlshortener.repository.ShortUrlRepository;import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShortUrlService {

    private final ShortUrlRepository repository;
    private final RedisTemplate<String, String> redisTemplate;


    public String createShortUrl(String originalUrl) {

        log.info("Creating short URL for: {}", originalUrl);

        // Step 1 — Check if already shortened
        Optional<ShortUrl> existing = repository.findByOriginalUrl(originalUrl);

        if (existing.isPresent()) {
            log.info("URL already shortened. Returning existing code: {}", existing.get().getShortCode());
            return existing.get().getShortCode();
        }

        // Step 2 — Generate new one safely
        for (int i = 0; i < 5; i++) {

            String code = Base62Encoder.generateShortCode(6);
            log.debug("Generated short code attempt {} : {}", i + 1, code);

            try {

                ShortUrl url = ShortUrl.builder()
                        .originalUrl(originalUrl)
                        .shortCode(code)
                        .createdAt(LocalDateTime.now())
                        .build();

                repository.save(url);

                log.info("Successfully created short URL. Code: {}", code);

                return code;

            } catch (DataIntegrityViolationException ex) {

                log.warn("Collision detected for code {}. Retrying...", code);
            }
        }

        log.error("Failed to generate unique short URL after retries");

        throw new RuntimeException("Unable to generate unique short URL");
    }


    public String getOriginalUrl(String shortCode) {

        log.debug("Fetching original URL for code: {}", shortCode);

        String cached = redisTemplate.opsForValue().get(shortCode);

        if (cached != null) {
            log.debug("Cache HIT for code: {}", shortCode);
            return cached;
        }

        log.debug("Cache MISS for code: {}. Fetching from DB.", shortCode);

        ShortUrl url = repository.findByShortCode(shortCode)
                .orElseThrow(() -> {
                    log.error("Short URL not found for code: {}", shortCode);
                    return new RuntimeException("URL not found");
                });

        redisTemplate.opsForValue()
                .set(shortCode, url.getOriginalUrl(), Duration.ofHours(24));

        log.debug("Stored URL in Redis cache for code: {}", shortCode);

        return url.getOriginalUrl();
    }
}
