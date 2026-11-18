package com.demo.ms_news.dto;

import java.time.LocalDateTime;

public record NewsDetailDto(Long id, String title, String summary, String content, LocalDateTime publishedAt, CategoryDto category) { }