package com.demo.ms_news.service;

import com.demo.ms_news.dto.*;
import com.demo.ms_news.entity.*;
import com.demo.ms_news.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsService {

    private final NewsRepository newsRepo;
    private final CategoryRepository catRepo;
    private final WishlistRepository wishRepo;

    public List<NewsDto> findAll() {
        return newsRepo.findAllByOrderByPublishedAtDesc().stream().map(this::toDto).toList();
    }

    public NewsDetailDto findById(Long id) {
        News n = newsRepo.findById(id)
            .orElseThrow(() -> new NotFoundException("News " + id + " not found"));
        return new NewsDetailDto(n.getId(), n.getTitle(), n.getSummary(), n.getContent(),
                                 n.getPublishedAt(), toDto(n.getCategory()));
    }

    public List<CategoryDto> findAllCategories() {
        return catRepo.findAll().stream().map(this::toDto).toList();
    }

    public CategoryDto findCategoryById(Long id) {
        return catRepo.findById(id).map(this::toDto)
            .orElseThrow(() -> new NotFoundException("Category " + id + " not found"));
    }

    public List<NewsDto> findWishlistOf(Long userId) {
        return wishRepo.findByUser(userId).stream().map(w -> toDto(w.getNews())).toList();
    }

    private NewsDto toDto(News n) {
        return new NewsDto(n.getId(), n.getTitle(), n.getSummary(),
                           n.getPublishedAt(), toDto(n.getCategory()));
    }

    private CategoryDto toDto(Category c) {
        return new CategoryDto(c.getId(), c.getName(), c.getSlug());
    }
}