package com.tinylink.shortener.controller;

import com.tinylink.shortener.dto.UrlRequest;
import com.tinylink.shortener.dto.UrlResponse;
import com.tinylink.shortener.service.UrlService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService service;

    @PostMapping("/api/shorten")
    public ResponseEntity<UrlResponse> shorten(@RequestBody @Valid UrlRequest request) {
        String code = service.shorten(request.url());
        String fullShortUrl = "http://localhost:8080/" + code;
        return ResponseEntity.ok(new UrlResponse(fullShortUrl));
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        String url = service.getOriginalUrl(code);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
}