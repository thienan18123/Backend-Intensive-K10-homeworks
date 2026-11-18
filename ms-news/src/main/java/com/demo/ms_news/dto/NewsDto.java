package com.demo.ms_news.dto;

import java.time.LocalDateTime;

public record NewsDto(Long id, String title, String summary, LocalDateTime publishedAt, CategoryDto category) { }