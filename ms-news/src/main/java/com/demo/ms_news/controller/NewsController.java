package com.demo.ms_news.controller;

import com.demo.ms_news.dto.*;
import com.demo.ms_news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewsController {

    private final NewsService service;

    @GetMapping("/news")
    public List<NewsDto> all() { 
        return service.findAll(); 

    }

    @GetMapping("/news/{id}")
    public NewsDetailDto one(@PathVariable Long id) { 
        return service.findById(id); 
    }

    @GetMapping("/cates")
    public List<CategoryDto> cates() { 
        return service.findAllCategories(); 

    }

    @GetMapping("/cates/{id}")
    public CategoryDto cate(@PathVariable Long id) { 
        return service.findCategoryById(id); 
    }
}