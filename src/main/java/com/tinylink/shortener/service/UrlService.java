package com.tinylink.shortener.service;

import com.tinylink.shortener.model.UrlEntity;
import com.tinylink.shortener.repository.UrlRepository;
import com.tinylink.shortener.util.Base62;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository repository;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public String shorten(String originalUrl) {
        UrlEntity entity = new UrlEntity();
        entity.setOriginalUrl(originalUrl);
        entity = repository.save(entity);

        String code = Base62.encode(entity.getId());
        entity.setShortCode(code);

        redisTemplate.opsForValue().set(code, originalUrl, 24, TimeUnit.HOURS);

        return code;
    }

    public String getOriginalUrl(String code) {
        String cachedUrl = redisTemplate.opsForValue().get(code);
        if (cachedUrl != null) return cachedUrl;

        String originalUrl = repository.findByShortCode(code)
                .map(UrlEntity::getOriginalUrl)
                .orElseThrow(() -> new RuntimeException("Link não encontrado"));

        redisTemplate.opsForValue().set(code, originalUrl, 24, TimeUnit.HOURS);

        return originalUrl;
    }
}