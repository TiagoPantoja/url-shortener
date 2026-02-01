package com.tinylink.shortener.dto;

import jakarta.validation.constraints.NotBlank;

public record UrlRequest(
        @NotBlank(message = "A URL não pode estar vazia")
        @org.hibernate.validator.constraints.URL(message = "URL inválida")
        String url
) {}